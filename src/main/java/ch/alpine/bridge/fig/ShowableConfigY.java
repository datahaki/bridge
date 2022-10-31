// code by jph
package ch.alpine.bridge.fig;

import java.awt.Rectangle;

import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;

public class ShowableConfigY extends ShowableConfig {
  public ShowableConfigY(Rectangle rectangle, CoordinateBoundingBox cbb) {
    super(rectangle, cbb);
  }

  @Override
  public double y_pos(Scalar y) {
    return rectangle.y + y.subtract(yRange.min()).multiply(y2pixel).number().doubleValue();
  }
}
