// code by jph
package ch.alpine.bridge.math;

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
  private final UnitSystem unitSystem;
  private final Unit unit;
  private final Scalar zero;
  private final ScalarUnaryOperator unitConvert;
  private final ScalarUnaryOperator quantityMagnitude;

  private UnitHub(UnitSystem unitSystem, Unit unit) {
    this.unitSystem = unitSystem;
    this.unit = unit;
    zero = equipWithUnit(RealScalar.ZERO);
    unitConvert = UnitConvert.of(unitSystem).to(unit);
    quantityMagnitude = QuantityMagnitude.of(unitSystem).in(unit);
  }

  public Unit unit() {
    return unit;
  }

  /** @param scalar not a quantity
   * @return Quantity.of(scalar, unit) */
  public Scalar equipWithUnit(Scalar scalar) {
    return Quantity.of(scalar, unit);
  }

  /** @param scalar not a quantity
   * @return Quantity.of(scalar, unit) */
  public Scalar equipWithUnit(Number number) {
    return Quantity.of(number, unit);
  }

  /** Example: 180 is interpreted as 180[deg] and mapped to pi
   * 
   * @param number with interpretation with given unit
   * @return corresponding unitless scalar */
  public Scalar equipWithUnitNormalize(Number number) {
    return unitSystem.apply(equipWithUnit(number));
  }

  /** this is equipWithUnit followed by unitSystem apply
   * 
   * @param scalar
   * @return */
  public Scalar equipWithUnitNormalize(Scalar scalar) {
    return unitSystem.apply(equipWithUnit(scalar));
  }

  /** Example: PERCENT.unitConvert(0.5) == 50 %
   * 
   * @param scalar with a compatible unit
   * @return quantity with this unit */
  public Scalar unitConvert(Scalar scalar) {
    return unitConvert.apply(scalar);
  }

  /** Example: PERCENT.unitConvert(0.5) == 50 %
   * 
   * @return op that maps unitless scalars to quantity with given unit */
  public ScalarUnaryOperator unitConvert() {
    return unitConvert;
  }

  /** @param scalar with a compatible unit
   * @return scalar with unit dropped after conversion to this unit */
  public Scalar quantityMagnitude(Scalar scalar) {
    return quantityMagnitude.apply(scalar);
  }

  /** @return */
  public ScalarUnaryOperator quantityMagnitude() {
    return quantityMagnitude;
  }

  /** @return Quantity[0, unit] */
  public Scalar zero() {
    return zero;
  }

  /** @param scalar
   * @return */
  public double doubleValue(Scalar scalar) {
    return quantityMagnitude(scalar).number().doubleValue();
  }

  /** @param scalar
   * @return */
  public float floatValue(Scalar scalar) {
    return quantityMagnitude(scalar).number().floatValue();
  }

  /** @param scalar with a compatible unit
   * @return long value of given scalar after conversion to this unit, rounding,
   * and projection to integer
   * @throws Exception if rounding results in an integer value outside the 64-bit range */
  public long longValue(Scalar scalar) {
    return Round.longValueExact(quantityMagnitude(scalar));
  }

  /** @param scalar
   * @return */
  public long longValueExact(Scalar scalar) {
    return Scalars.longValueExact(quantityMagnitude(scalar));
  }

  /** @param scalar with a compatible unit
   * @return int value of given scalar after conversion to this unit, rounding,
   * and projection to integer
   * @throws Exception if rounding results in an integer value outside the 32-bit range */
  public int intValue(Scalar scalar) {
    return Round.intValueExact(quantityMagnitude(scalar));
  }

  /** @param scalar
   * @return */
  public int intValueFloor(Scalar scalar) {
    return Scalars.intValueExact(Floor.FUNCTION.apply(quantityMagnitude(scalar)));
  }

  public int intValueExact(Scalar scalar) {
    return Scalars.intValueExact(quantityMagnitude(scalar));
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
