// code by jph
package ch.alpine.bridge.fig.grd;

import ch.alpine.tensor.Scalar;

public interface AxisMap {
  Scalar fwd(Scalar model);

  Scalar inv(Scalar range);
}
