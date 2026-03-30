// code by jph
package ch.alpine.bridge.gfx;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JComponent;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;

import ch.alpine.bridge.awt.AwtUtil;
import ch.alpine.bridge.awt.RenderQuality;
import ch.alpine.bridge.fig.Show;
import ch.alpine.bridge.fig.ShowOption;
import ch.alpine.bridge.fig.Showable;
import ch.alpine.bridge.fig.plt.ListLinePlot;
import ch.alpine.tensor.Rational;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.alg.Append;
import ch.alpine.tensor.alg.Dot;
import ch.alpine.tensor.ext.BoundedLinkedList;
import ch.alpine.tensor.ext.Int;
import ch.alpine.tensor.ext.Integers;
import ch.alpine.tensor.mat.DiagonalMatrix;
import ch.alpine.tensor.mat.re.Det;
import ch.alpine.tensor.mat.re.LinearSolve;
import ch.alpine.tensor.qty.Degree;
import ch.alpine.tensor.sca.Abs;
import ch.alpine.tensor.sca.Chop;
import ch.alpine.tensor.sca.pow.Power;
import ch.alpine.tensor.sca.pow.Sqrt;
import ch.alpine.tensor.sca.tri.ArcTan;

public final class GeometricComponent extends JComponent {
  private static final Scalar SCALE_FACTOR = Sqrt.FUNCTION.apply(RealScalar.TWO);
  private static final Scalar WHEEL_ANGLE = Degree.of(Rational.of(90, 6));
  // ---
  /** public access to final JComponent: attach mouse listeners, get/set properties, ... */
  private final IntervalClock intervalClock = new IntervalClock();
  private final List<RenderInterface> renderBackground = new CopyOnWriteArrayList<>();
  private final List<RenderInterface> renderInterfaces = new CopyOnWriteArrayList<>();
  private final BoundedLinkedList<Tensor> boundedLinkedList = new BoundedLinkedList<>(96);
  // ---
  /** 3x3 affine matrix that maps model to pixel coordinates */
  private Tensor model2pixel = PvmBuilder.rhs().setOffset(300, 300).digest();
  private Point mouseLocation = new Point();
  private int mouseWheel = 0;
  private boolean isZoomable = true;
  private boolean isRotatable = true;
  private int buttonDrag = MouseEvent.BUTTON3;
  private Color background = Color.WHITE;
  private boolean showTimings = false;

  public GeometricComponent() {
    addMouseWheelListener(event -> {
      final int delta = -event.getWheelRotation(); // either 1 or -1
      final int mods = event.getModifiersEx();
      final int mask = InputEvent.CTRL_DOWN_MASK; // 128 = 2^7
      if ((mods & mask) == 0) // ctrl pressed?
        mouseWheel += delta;
      else //
      if (isZoomable) {
        Scalar factor = Power.of(SCALE_FACTOR, delta);
        Tensor scale = DiagonalMatrix.of(factor, factor, RealScalar.ONE);
        Tensor shift = Tensors.vector(event.getX(), event.getY());
        shift = shift.subtract(shift.multiply(factor));
        scale.set(shift.Get(0), 0, 2);
        scale.set(shift.Get(1), 1, 2);
        model2pixel = scale.dot(model2pixel);
      }
      repaint();
    });
    MouseInputListener mouseInputListener = new MouseInputAdapter() {
      private Tensor down = null;
      private Tensor center = null;

      @Override
      public void mouseMoved(MouseEvent mouseEvent) {
        mouseLocation = mouseEvent.getPoint();
      }

      @Override
      public void mousePressed(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == buttonDrag) {
          down = toPixel(mouseEvent.getPoint());
          Dimension dimension = getSize();
          center = toModel(AwtUtil.center(dimension));
        }
      }

      @Override
      public void mouseDragged(MouseEvent mouseEvent) {
        mouseLocation = mouseEvent.getPoint();
        if (Objects.nonNull(down)) {
          Tensor now = toPixel(mouseEvent.getPoint());
          // ---
          final int mods = mouseEvent.getModifiersEx();
          final int mask = InputEvent.CTRL_DOWN_MASK; // 128 = 2^7
          if ((mods & mask) == 0 || !isRotatable()) {
            Tensor diff = now.subtract(down);
            model2pixel.set(diff.Get(0)::add, 0, 2);
            model2pixel.set(diff.Get(1)::add, 1, 2);
          } else {
            Dimension dimension = getSize();
            Tensor mid = toPixel(AwtUtil.center(dimension));
            Scalar ang = arcTan2(down.subtract(mid)).subtract(arcTan2(now.subtract(mid)));
            model2pixel = Dot.of( //
                model2pixel, //
                Se2Matrix.of(Append.of(center, ang)), //
                Se2Matrix.translation(center.negate()));
          }
          down = now;
          repaint();
        }
      }

      /** @param vector of the form {x, y, ...}
       * @return ArcTan[x, y] */
      private static Scalar arcTan2(Tensor vector) {
        return ArcTan.of(vector.Get(0), vector.Get(1));
      }

      @Override
      public void mouseReleased(MouseEvent mouseEvent) {
        down = null;
        center = null;
      }
    };
    addMouseMotionListener(mouseInputListener);
    addMouseListener(mouseInputListener);
  }

  public void setColorBackground(Color background) {
    this.background = background;
  }

  public void showTimings() {
    showTimings = true;
  }

  @Override
  protected void paintComponent(Graphics _g) {
    Dimension dimension = getSize();
    if (Objects.nonNull(background)) {
      _g.setColor(background);
      _g.fillRect(0, 0, dimension.width, dimension.height);
    }
    Scalar d_interv = intervalClock.seconds();
    {
      Graphics2D graphics = (Graphics2D) _g;
      RenderQuality.setQuality(graphics);
      GeometricLayer geometricLayer = new GeometricLayer(model2pixel);
      renderBackground.forEach(renderInterface -> renderInterface.render(geometricLayer, graphics));
      Integers.requireEquals(1, geometricLayer.deque_size());
      renderInterfaces.forEach(renderInterface -> renderInterface.render(geometricLayer, graphics));
      Integers.requireEquals(1, geometricLayer.deque_size());
    }
    Scalar d_render = intervalClock.seconds();
    boundedLinkedList.add(Tensors.of(d_render, d_interv));
    if (showTimings)
      timingPlot(_g, dimension);
  }

  private void timingPlot(Graphics _g, Dimension dimension) {
    Rectangle rectangle = new Rectangle(40, dimension.height - 60, 120, 60);
    _g.setColor(new Color(255, 255, 255, 128));
    _g.fillRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    Show show = new Show();
    show.set(ShowOption.AXIS_X, false);
    show.set(ShowOption.FRAMED, false);
    show.set(ShowOption.UNIT_MAPPING, false);
    {
      Int i = new Int();
      Tensor points = Tensor.of(boundedLinkedList.stream().map(row -> Tensors.of(RealScalar.of(i.getAndIncrement()), row.Get(0))));
      Showable showable = show.add(ListLinePlot.of(points));
      showable.setAlpha(192);
      showable.setStroke(new BasicStroke(0.5f));
      showable.setLabel("render");
    }
    {
      Int i = new Int();
      Tensor points = Tensor.of(boundedLinkedList.stream().map(row -> Tensors.of(RealScalar.of(i.getAndIncrement()), row.Get(1))));
      Showable showable = show.add(ListLinePlot.of(points));
      showable.setAlpha(192);
      showable.setStroke(new BasicStroke(0.5f));
      showable.setLabel("interv");
    }
    show.render(_g, rectangle);
  }

  protected boolean isRotatable() {
    Scalar rx = model2pixel.Get(0, 0);
    Scalar ry = model2pixel.Get(0, 1);
    return isRotatable //
        && rx.zero().equals(ry.zero());
  }

  /** determines if mouseDragged + ctrl allows rotation
   * 
   * @param isRotatable */
  public void setRotatable(boolean isRotatable) {
    this.isRotatable = isRotatable;
  }

  /** determines if mouse wheel + ctrl change magnification
   * 
   * @param isZoomable */
  public void setZoomable(boolean isZoomable) {
    this.isZoomable = isZoomable;
  }

  /** @param button for instance MouseEvent.BUTTON1 */
  public void setButtonDrag(int button) {
    buttonDrag = button;
  }

  public void addRenderInterface(RenderInterface renderInterface) {
    renderInterfaces.add(renderInterface);
  }

  /** @return {px, py, angle} in model space */
  public Tensor getMouseSe2CState() {
    return Append.of(toModel(mouseLocation), RealScalar.of(mouseWheel).multiply(WHEEL_ANGLE));
  }

  public void addRenderInterfaceBackground(RenderInterface renderInterface) {
    renderBackground.add(renderInterface);
  }

  // ---
  /** @param model2pixel with dimensions 3 x 3
   * @throws Exception if determinant of matrix is positive */
  public void setModel2Pixel(Tensor model2pixel) {
    this.model2pixel = model2pixel.copy(); // set matrix regardless of conditions
    // ---
    Scalar det = Det.of(model2pixel);
    if (Chop._10.isZero(Sqrt.FUNCTION.apply(Abs.FUNCTION.apply(det))))
      System.err.println("model2pixel must not be singular");
    // Sign.requirePositive(det.negate());
  }

  public Tensor getModel2Pixel() {
    return model2pixel.copy();
  }

  private Tensor toModel(Point point) {
    return LinearSolve.of(model2pixel, toPixel(point)).extract(0, 2);
  }

  private static Tensor toPixel(Point point) {
    return Tensors.vector(point.x, point.y, 1);
  }
}
