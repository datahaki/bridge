// code by jph
package ch.alpine.bridge.fig;

import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.sca.Clip;

class ConfLinDecr extends ConfLin {
  /** @param ofs
   * @param length is 2 or greater
   * @param clip with width non-zero */
  public ConfLinDecr(int ofs, int length, Clip clip) {
    super(ofs + length - 1, length, clip, Scalar::negate);
  }

  @Override
  public ConfLinDecr pruned() {
    return new ConfLinDecr(0, length(), clip());
  }
}
