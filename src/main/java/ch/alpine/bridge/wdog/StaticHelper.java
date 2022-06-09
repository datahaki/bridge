// code by jph
package ch.alpine.bridge.wdog;

import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.api.ScalarUnaryOperator;
import ch.alpine.tensor.qty.QuantityMagnitude;
import ch.alpine.tensor.qty.UnitSystem;

/* package */ enum StaticHelper {
  ;
  private static final ScalarUnaryOperator NANOS = QuantityMagnitude.SI().in("ns");

  /** @param scalar with unit compatible with "s", "ms", "us", "ns", ...
   * @return number of nano seconds encoded by scalar
   * @see UnitSystem#SI() */
  public static long nanos(Scalar scalar) {
    return NANOS.apply(scalar).number().longValue();
  }
}
