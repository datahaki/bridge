// code by jph
package ch.alpine.bridge.fig;

import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.sca.Clip;

class ConfDecr extends ConfBase {
  public ConfDecr(int x, int width, Clip xRange) {
    super(x + width - 1, width, xRange, Scalar::negate);
  }

  @Override
  public ConfDecr clipped() {
    return new ConfDecr(0, width, clip);
  }
}
