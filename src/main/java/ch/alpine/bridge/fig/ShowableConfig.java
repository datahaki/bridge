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
import ch.alpine.tensor.sca.Sign;

public class ShowableConfig {
  final Rectangle rectangle;
  private final CoordinateBoundingBox cbb;
  private final Clip xRange;
  protected final Clip yRange;
  private final double yBaseline;
  private final Scalar x2pixel;
  protected final Scalar y2pixel;
  private final Scalar pixel2x;
  private final Scalar pixel2y;

  public ShowableConfig(Rectangle rectangle, CoordinateBoundingBox cbb) {
    this.rectangle = rectangle;
    this.cbb = cbb;
    this.xRange = cbb.getClip(0);
    this.yRange = cbb.getClip(1);
    yBaseline = rectangle.y + rectangle.height - 1;
    x2pixel = RealScalar.of(rectangle.width - 1).divide(xRange.width());
    y2pixel = RealScalar.of(rectangle.height - 1).divide(yRange.width());
    Sign.requirePositive(x2pixel);
    Sign.requirePositive(y2pixel);
    pixel2x = xRange.width().divide(RealScalar.of(rectangle.width - 1));
    pixel2y = yRange.width().divide(RealScalar.of(rectangle.height - 1));
  }

  public final double x_pos(Scalar x) {
    return rectangle.x + x.subtract(xRange.min()).multiply(x2pixel).number().doubleValue();
  }

  public double y_pos(Scalar y) {
    return yBaseline - y.subtract(yRange.min()).multiply(y2pixel).number().doubleValue();
  }

  public final Point2D toPoint2D(Tensor vector) {
    return new Point2D.Double( //
        x_pos(vector.Get(0)), //
        y_pos(vector.Get(1)));
  }

  public final Optional<Tensor> toValue(Point point) {
    return rectangle.contains(point) //
        ? Optional.of(Tensors.of( //
            xRange.min().add(RealScalar.of(point.x - rectangle.x).multiply(pixel2x)), //
            yRange.min().add(RealScalar.of(yBaseline - point.y).multiply(pixel2y))))
        : Optional.empty();
  }

  public final Scalar dx(Scalar dx) {
    return dx.multiply(pixel2x);
  }

  public final Scalar dy(Scalar dy) {
    return dy.multiply(pixel2y);
  }

  public final Clip getClip(int index) {
    return cbb.getClip(index);
  }

  /** @return may be null */
  public final CoordinateBoundingBox getCbb() {
    return cbb;
  }
}
