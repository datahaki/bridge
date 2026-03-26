// code by jph
package ch.alpine.bridge.fig;

import ch.alpine.tensor.sca.Clip;

class ConfIncr extends ConfBase {
  /** @param ofs
   * @param length is 2 or greater
   * @param clip with width non-zero */
  public ConfIncr(int ofs, int length, Clip clip) {
    super(ofs, length, clip, s -> s);
  }

  @Override
  public ConfIncr pruned() {
    return new ConfIncr(0, length(), clip());
  }
}
