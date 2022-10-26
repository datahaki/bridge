// code by jph
package ch.alpine.bridge.fig;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.util.Optional;

import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;
import ch.alpine.tensor.sca.Clip;

public class ShowableConfig {
  final Rectangle rectangle;
  private final CoordinateBoundingBox cbb;
  private final Clip xRange;
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
    // FIXME check if x/y Range width == 0
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

  public Optional<Tensor> toValue(Point point) {
    return rectangle.contains(point) //
        ? Optional.of(Tensors.of( //
            xRange.min().add(RealScalar.of(point.x - rectangle.x).divide(x_factor)), //
            yRange.min().add(RealScalar.of(y_height - point.y).divide(y_factor)) //
        ))
        : Optional.empty();
  }

  public Scalar dx(Scalar dx) {
    return dx.divide(x_factor);
  }

  public Scalar dy(Scalar dy) {
    return dy.divide(y_factor);
  }

  public Clip getClip(int index) {
    return cbb.getClip(index);
  }

  /** @return may be null */
  public CoordinateBoundingBox getCbb() {
    return cbb;
  }
}
