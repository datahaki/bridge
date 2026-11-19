// code by jph
package ch.alpine.bridge.fig.grd;

import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.sca.exp.Exp;
import ch.alpine.tensor.sca.exp.Log;

public enum AxisMaps implements AxisMap {
  LIN {
    @Override
    public Scalar model2range(Scalar model) {
      return model;
    }

    @Override
    public Scalar range2model(Scalar range) {
      return range;
    }

    @Override
    public TickOption tickOption() {
      return TickOption.RANGE;
    }
  },
  LOG {
    @Override
    public Scalar model2range(Scalar model) {
      return Log.FUNCTION.apply(model);
    }

    @Override
    public Scalar range2model(Scalar range) {
      return Exp.FUNCTION.apply(range);
    }

    @Override
    public TickOption tickOption() {
      return TickOption.RANGE;
    }
  }
}
