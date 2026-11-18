package ch.alpine.bridge.fig.grd;

import ch.alpine.tensor.api.ScalarUnaryOperator;
import ch.alpine.tensor.sca.exp.Exp;
import ch.alpine.tensor.sca.exp.Log;

public enum AxisMap {
  LIN(t -> t, t -> t),
  LOG(Log.FUNCTION, Exp.FUNCTION),
  LAT(null, null);

  public final ScalarUnaryOperator fwd;
  public final ScalarUnaryOperator inv;

  private AxisMap(ScalarUnaryOperator fwd, ScalarUnaryOperator inv) {
    this.fwd = fwd;
    this.inv = inv;
  }
}
