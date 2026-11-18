// code by jph
package ch.alpine.bridge.math;

import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.api.ScalarUnaryOperator;
import ch.alpine.tensor.qty.Quantity;
import ch.alpine.tensor.qty.QuantityMagnitude;
import ch.alpine.tensor.qty.Unit;
import ch.alpine.tensor.qty.UnitConvert;
import ch.alpine.tensor.qty.UnitSystem;

/** purpose is to map magnitudes such as 180 (as in 180[deg]) to their
 * unitless value (e.g. 180[deg] == pi)
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Degree.html">Degree</a> */
// TODO should be merged with SI and UnitHub
public enum Unitless {
  /** the equivalent conversion formula in Mathematica is
   * QuantityMagnitude[Quantity[1, "Degrees"], "Radians"] */
  DEGREES("deg"),
  TURNS("turns"),
  PERCENT("%"),
  PERMILLE("‰");

  private final Unit unit;
  private final ScalarUnaryOperator unitConvert;
  private final ScalarUnaryOperator quantityMagnitude;

  Unitless(String string) {
    unit = Unit.of(string);
    unitConvert = UnitConvert.SI().to(unit);
    quantityMagnitude = QuantityMagnitude.SI().in(unit);
  }

  /** Example: 180 is interpreted as 180[deg] and mapped to pi
   * 
   * @param number with interpretation with given unit
   * @return corresponding unitless scalar */
  public Scalar of(Number number) {
    return of(RealScalar.of(number));
  }

  /** @param scalar
   * @return */
  public Scalar of(Scalar scalar) {
    return UnitSystem.SI().apply(Quantity.of(scalar, unit));
  }

  /** EXPERIMENTAL
   * 
   * @param number
   * @return */
  public Scalar asIs(Number number) {
    return Quantity.of(number, unit);
  }

  /** EXPERIMENTAL
   * 
   * @param scalar
   * @return */
  public Scalar asIs(Scalar scalar) {
    return Quantity.of(scalar, unit);
  }

  /** EXPERIMENTAL
   * 
   * Example: PERCENT.unitConvert(0.5) == 50 %
   * 
   * @return op that maps unitless scalars to quantity with given unit */
  public ScalarUnaryOperator unitConvert() {
    return unitConvert;
  }

  /** EXPERIMENTAL
   * 
   * @return */
  public ScalarUnaryOperator quantityMagnitude() {
    return quantityMagnitude;
  }
}
