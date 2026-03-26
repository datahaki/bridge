// code by jph
package ch.alpine.bridge.fig;

import ch.alpine.tensor.sca.Clip;

class ConfIncr extends ConfBase {
  public ConfIncr(int ofs, int length, Clip clip) {
    super(ofs, length, clip, s -> s);
  }

  @Override
  public ConfIncr clipped() {
    return new ConfIncr(0, length, clip);
  }
}
