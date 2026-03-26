// code by jph
package ch.alpine.bridge.fig;

import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.sca.Clip;

class ConfDecr extends ConfBase {
  public ConfDecr(int ofs, int length, Clip clip) {
    super(ofs + length - 1, length, clip, Scalar::negate);
  }

  @Override
  public ConfDecr clipped() {
    return new ConfDecr(0, length(), clip());
  }
}
