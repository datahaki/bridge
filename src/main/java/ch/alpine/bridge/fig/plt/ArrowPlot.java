// code by jph
package ch.alpine.bridge.fig.plt;

import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.Optional;

import ch.alpine.bridge.fig.BaseShowable;
import ch.alpine.bridge.fig.ShowableConfig;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;
import ch.alpine.tensor.opt.nd.CoordinateBounds;

public class ArrowPlot extends BaseShowable {
  // private static final double RADIUS = 2.5;
  private final Line2D.Double line = new Line2D.Double();
  private final Tensor src;
  private final Tensor dst;

  public ArrowPlot(Tensor p0, Tensor p1) {
    this.src = p0;
    this.dst = p1;
  }

  @Override
  public void render(ShowableConfig showableConfig, Graphics2D graphics) {
    graphics.setColor(getColor());
    graphics.setStroke(getStroke());
    line.setLine( //
        showableConfig.toPoint2D(src), //
        showableConfig.toPoint2D(dst));
    graphics.draw(line);
    // graphics.setStroke(new BasicStroke());
    // double radius = RADIUS;
    // graphics.fill(new Ellipse2D.Double(point1.getX() - radius, point1.getY() - radius, 2 * radius, 2 * radius));
  }

  @Override
  public Optional<CoordinateBoundingBox> fullPlotRange() {
    return Optional.of(CoordinateBounds.of(Tensors.of(src, dst)));
  }
}
