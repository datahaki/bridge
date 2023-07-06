// code by jph
package ch.alpine.bridge.lang;

import java.io.Serializable;

import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Scalars;
import ch.alpine.tensor.api.ScalarUnaryOperator;
import ch.alpine.tensor.ext.Integers;
import ch.alpine.tensor.io.MathematicaFormat;
import ch.alpine.tensor.qty.Quantity;
import ch.alpine.tensor.qty.QuantityMagnitude;
import ch.alpine.tensor.qty.Unit;
import ch.alpine.tensor.qty.UnitConvert;
import ch.alpine.tensor.qty.UnitSystem;
import ch.alpine.tensor.sca.Floor;
import ch.alpine.tensor.sca.Round;

public class UnitHub implements Serializable {
  /** @param unitSystem
   * @param unit
   * @return */
  public static UnitHub of(UnitSystem unitSystem, Unit unit) {
    return new UnitHub(unitSystem, unit);
  }

  /** @param unit
   * @return */
  public static UnitHub si(Unit unit) {
    return of(UnitSystem.SI(), unit);
  }

  /** @param string for instance "m*s^-1"
   * @return */
  public static UnitHub si(String string) {
    return si(Unit.of(string));
  }

  // ---
  private final Unit unit;
  private final Scalar zero;
  private final ScalarUnaryOperator convert;
  private final ScalarUnaryOperator magnitude;

  private UnitHub(UnitSystem unitSystem, Unit unit) {
    this.unit = unit;
    zero = quantity(RealScalar.ZERO);
    convert = UnitConvert.of(unitSystem).to(unit);
    magnitude = QuantityMagnitude.of(unitSystem).in(unit);
  }

  public Unit unit() {
    return unit;
  }

  /** @param scalar not a quantity
   * @return Quantity.of(scalar, unit) */
  public Scalar quantity(Scalar scalar) {
    return Quantity.of(scalar, unit);
  }

  /** @param scalar not a quantity
   * @return Quantity.of(scalar, unit) */
  public Scalar quantity(Number number) {
    return Quantity.of(number, unit);
  }

  /** @return Quantity[0, unit] */
  public Scalar zero() {
    return zero;
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

  /** @param scalar
   * @return */
  public double doubleValue(Scalar scalar) {
    return magnitude(scalar).number().doubleValue();
  }

  /** @param scalar
   * @return */
  public float floatValue(Scalar scalar) {
    return magnitude(scalar).number().floatValue();
  }

  /** @param scalar with a compatible unit
   * @return long value of given scalar after conversion to this unit, rounding,
   * and projection to integer
   * @throws Exception if rounding results in an integer value outside the 64-bit range */
  public long longValue(Scalar scalar) {
    return Scalars.longValueExact(Round.FUNCTION.apply(magnitude(scalar)));
  }

  /** @param scalar
   * @return */
  public long longValueExact(Scalar scalar) {
    return Scalars.longValueExact(magnitude(scalar));
  }

  /** @param scalar with a compatible unit
   * @return int value of given scalar after conversion to this unit, rounding,
   * and projection to integer
   * @throws Exception if rounding results in an integer value outside the 32-bit range */
  public int intValue(Scalar scalar) {
    return Scalars.intValueExact(Round.FUNCTION.apply(magnitude(scalar)));
  }

  /** @param scalar
   * @return */
  public int intValueFloor(Scalar scalar) {
    return Scalars.intValueExact(Floor.FUNCTION.apply(magnitude(scalar)));
  }

  public int intValueExact(Scalar scalar) {
    return Scalars.intValueExact(magnitude(scalar));
  }

  /** @param scalar with a compatible unit
   * @return byte value of given scalar after conversion to this unit, rounding,
   * and projection to integer
   * @throws Exception if rounding results in an integer value outside the 8-bit range */
  public byte byteValue(Scalar scalar) {
    int intValue = intValue(scalar);
    byte value = (byte) intValue;
    Integers.requireEquals(value, intValue);
    return value;
  }

  @Override
  public String toString() {
    return MathematicaFormat.concise("UnitHub", unit);
  }
}
