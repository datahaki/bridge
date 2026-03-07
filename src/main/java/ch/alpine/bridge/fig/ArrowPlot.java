// code by jph
package ch.alpine.bridge.fig;

import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.Optional;

import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;

public class ArrowPlot extends BaseShowable {
  private final Tensor src;
  private final Tensor dst;

  public ArrowPlot(Tensor p0, Tensor p1) {
    this.src = p0;
    this.dst = p1;
  }

  @Override
  public void render(ShowableConfig showableConfig, Graphics2D graphics) {
    graphics.setColor(getColor());
    Point2D point0 = showableConfig.toPoint2D(src);
    Point2D point1 = showableConfig.toPoint2D(dst);
    Path2D.Double path = new Path2D.Double();
    path.moveTo(point0.getX(), point0.getY());
    path.lineTo(point1.getX(), point1.getY());
    graphics.draw(path);
  }

  @Override
  public Optional<CoordinateBoundingBox> fullPlotRange() {
    return Optional.empty();
  }
}
