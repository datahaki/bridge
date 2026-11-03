// code by jph
package ch.alpine.bridge.fig;

import ch.alpine.tensor.sca.Clip;

class ConfLinIncr extends ConfLin {
  /** @param ofs
   * @param length is 2 or greater
   * @param clip with width non-zero */
  public ConfLinIncr(int ofs, int length, Clip clip) {
    super(ofs, length, clip, s -> s);
  }

  @Override
  public ConfLinIncr pruned() {
    return new ConfLinIncr(0, length(), clip());
  }
}
