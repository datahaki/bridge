// code by jph
package ch.alpine.bridge.fig.grd;

import ch.alpine.tensor.Scalar;

public interface AxisMap {
  Scalar model2range(Scalar model);

  Scalar range2model(Scalar range);

  TickOption tickOption();

  String format(Scalar ticks);
}
