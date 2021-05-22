// code by jph
package ch.alpine.java.wdog;

import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.api.ScalarUnaryOperator;
import ch.alpine.tensor.qty.QuantityMagnitude;

enum StaticHelper {
  ;
  private static final ScalarUnaryOperator NANOS = QuantityMagnitude.SI().in("ns");

  public static long toNanos(Scalar scalar) {
    return NANOS.apply(scalar).number().longValue();
  }
}
