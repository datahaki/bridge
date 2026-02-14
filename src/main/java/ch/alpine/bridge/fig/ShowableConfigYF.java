// code by jph
package ch.alpine.bridge.fig;

import java.awt.Rectangle;

import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;

public class ShowableConfigYF extends ShowableConfig {
  public ShowableConfigYF(Rectangle rectangle, CoordinateBoundingBox cbb) {
    super(rectangle, cbb);
  }

  @Override
  protected Scalar y_val(int point_y) {
    return yRange.min().add(RealScalar.of(point_y - rectangle.y).multiply(pixel2y));
  }

  @Override
  public double y_pos(Scalar y) {
    return rectangle.y + y.subtract(yRange.min()).multiply(y2pixel).number().doubleValue();
  }

  @Override
  public Scalar dy(Scalar dy) {
    return super.dy(dy).negate();
  }
}
