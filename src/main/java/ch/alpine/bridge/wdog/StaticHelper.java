// code by jph
package ch.alpine.bridge.wdog;

import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.api.ScalarUnaryOperator;
import ch.alpine.tensor.qty.QuantityMagnitude;
import ch.alpine.tensor.qty.UnitSystem;
import ch.alpine.tensor.sca.Round;

enum StaticHelper {
  ;
  private static final ScalarUnaryOperator NANOS = QuantityMagnitude.SI().in("ns");

  /** @param scalar with unit compatible with "s", "ms", "us", "ns", ...
   * @return number of nanoseconds encoded by scalar
   * @throws Exception if time duration cannot be encoded using long precision
   * @see UnitSystem#SI() */
  public static long nanos(Scalar scalar) {
    return Round.longValueExact(NANOS.apply(scalar));
  }
}
