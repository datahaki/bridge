// code by jph
package ch.alpine.bridge.fig.grd;

import ch.alpine.bridge.lang.UnicodeString;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Unprotect;
import ch.alpine.tensor.chq.IntegerQ;
import ch.alpine.tensor.sca.N;

public enum TickToString {
  ;
  public static String formatValue(Scalar value) {
    Scalar display = Unprotect.withoutUnit(value);
    Scalar scalar = IntegerQ.of(display) //
        ? display
        : N.DOUBLE.apply(display); // prevent fractions
    return UnicodeString.of(scalar);
  }
}
