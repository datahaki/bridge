// code by jph
package ch.alpine.bridge.fig;

import ch.alpine.tensor.sca.Clip;

class ConfIncr extends ConfBase {
  public ConfIncr(int x, int width, Clip xRange) {
    super(x, width, xRange, s -> s);
  }

  @Override
  public ConfIncr clipped() {
    return new ConfIncr(0, width, clip);
  }
}
