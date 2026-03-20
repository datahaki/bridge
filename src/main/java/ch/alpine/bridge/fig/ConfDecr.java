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
  public final double pixel(Scalar x) {
    return xBaseline - x.subtract(clip.min()).multiply(model2pixel).number().doubleValue();
  }

  @Override
  public Scalar model(int pixel) {
    return clip.min().add(RealScalar.of(xBaseline - pixel).multiply(pixel2model));
  }

  @Override
  public ConfDecr clipped() {
    return new ConfDecr(0, width, clip);
  }

  @Override
  public Scalar dx(Scalar dx) {
    return dx.multiply(pixel2model).negate();
  }
}
