// code by jph
package ch.alpine.bridge.gfx;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
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
import ch.alpine.bridge.lang.UnicodeString;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.alg.Append;
import ch.alpine.tensor.alg.Array;
import ch.alpine.tensor.alg.Dot;
import ch.alpine.tensor.ext.Integers;
import ch.alpine.tensor.mat.DiagonalMatrix;
import ch.alpine.tensor.mat.re.Det;
import ch.alpine.tensor.mat.re.LinearSolve;
import ch.alpine.tensor.qty.Degree;
import ch.alpine.tensor.sca.Chop;
import ch.alpine.tensor.sca.Round;
import ch.alpine.tensor.sca.Sign;
import ch.alpine.tensor.sca.pow.Power;
import ch.alpine.tensor.sca.pow.Sqrt;
import ch.alpine.tensor.sca.tri.ArcTan;

public final class GeometricComponent extends JComponent {
  private static final Scalar SCALE_FACTOR = Sqrt.FUNCTION.apply(RealScalar.TWO);
  private static final Font FONT_DEFAULT = new Font(Font.DIALOG, Font.PLAIN, 12);
  private static final Scalar WHEEL_ANGLE = Degree.of(15);
  // ---
  /** public access to final JComponent: attach mouse listeners, get/set properties, ... */
  private final IntervalClock intervalClock = new IntervalClock();
  private final List<RenderInterface> renderBackground = new CopyOnWriteArrayList<>();
  private final List<RenderInterface> renderInterfaces = new CopyOnWriteArrayList<>();
  // ---
  /** 3x3 affine matrix that maps model to pixel coordinates */
  private Tensor model2pixel = PvmBuilder.rhs().setOffset(300, 300).digest();
  private Tensor mouseLocation = Array.zeros(2);
  private int mouseWheel = 0;
  private boolean isZoomable = true;
  private boolean isRotatable = true;
  // private boolean printPositionOnClick = true;
  private int buttonDrag = MouseEvent.BUTTON3;

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
    {
      MouseInputListener mouseInputListener = new MouseInputAdapter() {
        private Tensor down = null;
        private Tensor center = null;

        @Override
        public void mouseMoved(MouseEvent mouseEvent) {
          mouseLocation = toModel(mouseEvent.getPoint());
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
          mouseLocation = toModel(mouseEvent.getPoint());
          if (Objects.nonNull(down)) {
            Tensor now = toPixel(mouseEvent.getPoint());
            // ---
            final int mods = mouseEvent.getModifiersEx();
            final int mask = InputEvent.CTRL_DOWN_MASK; // 128 = 2^7
            if ((mods & mask) == 0 || !isRotatable()) {
              Tensor diff = now.subtract(down);
              model2pixel.set(diff.get(0)::add, 0, 2);
              model2pixel.set(diff.get(1)::add, 1, 2);
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
  }

  private Color background = Color.WHITE;

  public void setColorBackground(Color background) {
    this.background = background;
  }

  @Override
  protected void paintComponent(Graphics _g) {
    Dimension dimension = getSize();
    if (Objects.nonNull(background)) {
      _g.setColor(background);
      _g.fillRect(0, 0, dimension.width, dimension.height);
    }
    {
      Graphics2D graphics = (Graphics2D) _g;
      RenderQuality.setQuality(graphics);
      GeometricLayer geometricLayer = new GeometricLayer(model2pixel);
      renderBackground.forEach(renderInterface -> renderInterface.render(geometricLayer, graphics));
      Integers.requireEquals(1, geometricLayer.deque_size());
      renderInterfaces.forEach(renderInterface -> renderInterface.render(geometricLayer, graphics));
      Integers.requireEquals(1, geometricLayer.deque_size());
    }
    {
      Graphics graphics = _g.create();
      graphics.setFont(FONT_DEFAULT);
      graphics.setColor(Color.LIGHT_GRAY);
      graphics.drawString(UnicodeString.of(Round._1.apply(intervalClock.hertz())), 0, 10);
      graphics.dispose();
    }
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
    Scalar scalar = RealScalar.of(mouseWheel).multiply(WHEEL_ANGLE);
    return Append.of(mouseLocation, scalar);
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
    if (Chop._08.isZero(det))
      System.err.println("model2pixel must not be singular");
    Sign.requirePositive(det.negate());
  }

  public Tensor getModel2Pixel() {
    return model2pixel.copy();
  }

  private Tensor toModel(Point point) {
    return LinearSolve.of(model2pixel, Tensors.vector(point.x, point.y, 1)).extract(0, 2);
  }

  private static Tensor toPixel(Point point) {
    return Tensors.vector(point.x, point.y);
  }
}
