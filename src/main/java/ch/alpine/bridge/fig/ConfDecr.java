// code by jph
package ch.alpine.bridge.fig;

import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.sca.Clip;

public class ConfDecr extends ConfBase {
  public ConfDecr(int x, int width, Clip xRange) {
    super(x, width, xRange, x + width - 1);
  }

  @Override
  public final double x_pos(Scalar x) {
    return xBaseline - x.subtract(xRange.min()).multiply(x2pixel).number().doubleValue();
  }

  @Override
  public Scalar value(int point_x) {
    return xRange.min().add(RealScalar.of(xBaseline - point_x).multiply(pixel2x));
  }

  @Override
  public ConfDecr clipped() {
    return new ConfDecr(0, width, xRange);
  }
}
