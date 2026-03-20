// code by jph
package ch.alpine.bridge.fig;

import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.sca.Clip;

public class ConfIncr extends ConfBase {
  public ConfIncr(int x, int width, Clip xRange) {
    super(x, width, xRange, x);
  }

  @Override
  public final double x_pos(Scalar x) {
    return xBaseline + x.subtract(xRange.min()).multiply(x2pixel).number().doubleValue();
  }

  @Override
  public Scalar value(int point_x) {
    return xRange.min().add(RealScalar.of(point_x - x).multiply(pixel2x));
  }

  @Override
  public ConfIncr clipped() {
    return new ConfIncr(0, width, xRange);
  }
}
