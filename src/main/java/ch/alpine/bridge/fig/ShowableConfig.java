// code by jph
package ch.alpine.bridge.fig;

import java.awt.Rectangle;
import java.awt.geom.Point2D;

import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;
import ch.alpine.tensor.sca.Clip;

public class ShowableConfig {
  final Rectangle rectangle;
  final CoordinateBoundingBox cbb;
  final Clip xRange;
  private final Clip yRange;
  private final double y_height; // TODO misnomer
  private final Scalar x_factor;
  private final Scalar y_factor;

  public ShowableConfig(Rectangle rectangle, CoordinateBoundingBox cbb) {
    this.rectangle = rectangle;
    this.cbb = cbb;
    this.xRange = cbb.getClip(0);
    this.yRange = cbb.getClip(1);
    y_height = rectangle.y + rectangle.height - 1;
    x_factor = RealScalar.of(rectangle.width - 1).divide(xRange.width());
    y_factor = RealScalar.of(rectangle.height - 1).divide(yRange.width());
  }

  public double x_pos(Scalar x) {
    return rectangle.x + x.subtract(xRange.min()).multiply(x_factor).number().doubleValue();
  }

  public double y_pos(Scalar y) {
    return y_height - y.subtract(yRange.min()).multiply(y_factor).number().doubleValue();
  }

  public Point2D.Double toPoint2D(Tensor vector) {
    return new Point2D.Double( //
        x_pos(vector.Get(0)), //
        y_pos(vector.Get(1)));
  }
}
