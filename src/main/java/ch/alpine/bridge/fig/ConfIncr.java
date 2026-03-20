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
  public final double pixel(Scalar x) {
    return xBaseline + x.subtract(clip.min()).multiply(model2pixel).number().doubleValue();
  }

  @Override
  public Scalar model(int pixel) {
    return clip.min().add(RealScalar.of(pixel - x).multiply(pixel2model));
  }

  @Override
  public ConfIncr clipped() {
    return new ConfIncr(0, width, clip);
  }

  @Override
  public Scalar dx(Scalar dx) {
    return dx.multiply(pixel2model);
  }
}
