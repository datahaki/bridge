// code by jph
package ch.alpine.bridge.fig;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.EnumSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import javax.swing.JComponent;

import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;
import ch.alpine.tensor.sca.Clip;
import ch.alpine.tensor.sca.Clips;
import ch.alpine.tensor.sca.pow.Power;

public class ShowComponent extends JComponent implements MouseMotionListener, MouseWheelListener {
  public enum Option {
    PAN,
    ZOOM
  }

  private final Set<Option> set_x = EnumSet.allOf(Option.class);
  private final Set<Option> set_y = EnumSet.allOf(Option.class);
  private Show show;
  private ShowableConfig showableConfig;
  private Point pressed = null;

  public ShowComponent() {
    addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent mouseEvent) {
        if (Objects.nonNull(showableConfig)) {
          Optional<Tensor> optional = showableConfig.toValue(mouseEvent.getPoint());
          optional.ifPresent(System.out::println);
          // if (optional.isPresent()) {
          // System.out.println(optional);
          // }
        }
      }

      //
      //
      @Override
      public void mousePressed(MouseEvent mouseEvent) {
        pressed = mouseEvent.getPoint();
      }

      @Override
      public void mouseReleased(MouseEvent e) {
        pressed = null;
      }
    });
    addMouseMotionListener(this);
    addMouseWheelListener(this);
  }

  /** @param show may be null */
  public synchronized void setShow(Show show) {
    this.show = show;
    showableConfig = null;
  }

  @Override
  protected synchronized void paintComponent(Graphics graphics) {
    // graphics.setFont(FONT);
    Dimension dimension = getSize();
    graphics.setColor(Color.WHITE);
    graphics.fillRect(0, 0, dimension.width, dimension.height);
    // TODO BRIDGE consider font when computing rectangle
    if (Objects.nonNull(show))
      showableConfig = show.render(graphics, Show.defaultInsets(dimension, graphics.getFont().getSize()));
  }

  @Override
  public synchronized void mouseWheelMoved(MouseWheelEvent mouseWheelEvent) {
    if (Objects.nonNull(showableConfig)) {
      Optional<Tensor> optional = showableConfig.toValue(mouseWheelEvent.getPoint());
      if (optional.isPresent()) {
        Tensor xy = optional.orElseThrow();
        CoordinateBoundingBox cbb = showableConfig.getCbb();
        Scalar factor = Power.of(1.3, mouseWheelEvent.getWheelRotation());
        if (set_x.contains(Option.ZOOM)) {
          Clip xclip = cbb.getClip(0);
          Scalar xofs = xy.Get(0);
          Scalar lo = xclip.min().subtract(xofs).multiply(factor);
          Scalar hi = xclip.max().subtract(xofs).multiply(factor);
          cbb = CoordinateBoundingBox.of( //
              Clips.interval(xofs.add(lo), xofs.add(hi)), //
              cbb.getClip(1));
        }
        if (set_y.contains(Option.ZOOM)) {
          Clip yclip = cbb.getClip(1);
          Scalar yofs = xy.Get(1);
          Scalar lo = yclip.min().subtract(yofs).multiply(factor);
          Scalar hi = yclip.max().subtract(yofs).multiply(factor);
          cbb = CoordinateBoundingBox.of( //
              cbb.getClip(0), //
              Clips.interval(yofs.add(lo), yofs.add(hi)));
        }
        show.setCbb(cbb);
        repaint();
      }
    }
  }

  @Override
  public void mouseMoved(MouseEvent e) {
    // ---
  }

  @Override
  public synchronized void mouseDragged(MouseEvent mouseEvent) {
    if (Objects.nonNull(pressed)) {
      if (Objects.nonNull(showableConfig)) {
        CoordinateBoundingBox cbb = showableConfig.getCbb();
        if (Objects.nonNull(cbb)) {
          Point point = mouseEvent.getPoint();
          int dx = point.x - pressed.x;
          int dy = pressed.y - point.y;
          pressed = point;
          // ---
          if (set_x.contains(Option.PAN)) {
            Scalar shift = showableConfig.dx(RealScalar.of(-dx));
            Clip xRange = cbb.getClip(0);
            cbb = CoordinateBoundingBox.of( //
                Clips.interval(xRange.min().add(shift), xRange.max().add(shift)), //
                cbb.getClip(1));
          }
          if (set_y.contains(Option.PAN)) {
            Scalar shift = showableConfig.dy(RealScalar.of(-dy));
            Clip yRange = cbb.getClip(1);
            cbb = CoordinateBoundingBox.of( //
                cbb.getClip(0), //
                Clips.interval(yRange.min().add(shift), yRange.max().add(shift)));
          }
          // ---
          show.setCbb(cbb);
          repaint();
        }
      }
    } else
      System.err.println("should not happen");
  }

  public void setOptionX(Option option, boolean status) {
    if (status)
      set_x.add(option);
    else
      set_x.remove(option);
  }

  public void setOptionY(Option option, boolean status) {
    if (status)
      set_y.add(option);
    else
      set_y.remove(option);
  }
}
