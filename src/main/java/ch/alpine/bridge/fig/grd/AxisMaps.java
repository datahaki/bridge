// code by jph
package ch.alpine.bridge.fig.grd;

import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.sca.exp.Exp;
import ch.alpine.tensor.sca.exp.Log;

public enum AxisMaps implements AxisMap {
  LIN {
    @Override
    public Scalar fwd(Scalar model) {
      return model;
    }

    @Override
    public Scalar inv(Scalar range) {
      return range;
    }
  },
  LOG {
    @Override
    public Scalar fwd(Scalar model) {
      return Log.FUNCTION.apply(model);
    }

    @Override
    public Scalar inv(Scalar range) {
      return Exp.FUNCTION.apply(range);
    }
  }
}
