// code by jph
package ch.alpine.bridge.lang;

import java.io.Serializable;

import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Scalars;
import ch.alpine.tensor.api.ScalarUnaryOperator;
import ch.alpine.tensor.io.MathematicaFormat;
import ch.alpine.tensor.qty.Quantity;
import ch.alpine.tensor.qty.QuantityMagnitude;
import ch.alpine.tensor.qty.Unit;
import ch.alpine.tensor.qty.UnitConvert;
import ch.alpine.tensor.sca.Round;

// TODO BRIDGE API class name ? UnitPivot ?
public class UnitHub implements Serializable {
  private final Unit unit;
  private final ScalarUnaryOperator convert;
  private final ScalarUnaryOperator magnitude;

  public UnitHub(String string) {
    unit = Unit.of(string);
    convert = UnitConvert.SI().to(unit);
    magnitude = QuantityMagnitude.SI().in(unit);
  }

  public Unit unit() {
    return unit;
  }

  /** @param scalar not a quantity
   * @return Quantity.of(scalar, unit) */
  public Scalar quantity(Scalar scalar) {
    return Quantity.of(scalar, unit);
  }

  public Scalar quantity(Number number) {
    return Quantity.of(number, unit);
  }

  /** @param scalar with a compatible unit
   * @return quantity with this unit */
  public Scalar convert(Scalar scalar) {
    return convert.apply(scalar);
  }

  /** @param scalar with a compatible unit
   * @return scalar with unit dropped after conversion to this unit */
  public Scalar magnitude(Scalar scalar) {
    return magnitude.apply(scalar);
  }

  public int intValue(Scalar scalar) {
    return Scalars.intValueExact(Round.FUNCTION.apply(magnitude(scalar)));
  }

  public long longValue(Scalar scalar) {
    return Scalars.longValueExact(Round.FUNCTION.apply(magnitude(scalar)));
  }

  public float floatValue(Scalar scalar) {
    return magnitude(scalar).number().floatValue();
  }

  public double doubleValue(Scalar scalar) {
    return magnitude(scalar).number().doubleValue();
  }

  @Override
  public String toString() {
    return MathematicaFormat.concise("UnitHub", unit);
  }
}
