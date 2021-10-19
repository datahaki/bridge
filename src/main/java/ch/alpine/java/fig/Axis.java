// code by jph
package ch.alpine.java.fig;

import java.io.Serializable;
import java.util.Objects;

import ch.alpine.java.lang.PrettyUnit;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.api.ScalarUnaryOperator;
import ch.alpine.tensor.qty.QuantityMagnitude;
import ch.alpine.tensor.qty.Unit;

/** <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Axis.html">Axis</a> */
public class Axis implements Serializable {
  private String label = "";
  private Unit unit = null;

  /** @param label of axis */
  public void setLabel(String string) {
    label = string;
  }

  /** @return label of axis */
  public String getLabel() {
    return label;
  }

  public void setUnit(Unit unit) {
    this.unit = unit;
  }

  public Unit getUnit() {
    return unit;
  }

  // ---
  /** @return label combined with unit */
  /* package */ String getAxisLabel() {
    return (getLabel() + " " + getUnitString()).strip();
  }

  /* package */ boolean hasUnit() {
    return Objects.nonNull(unit);
  }

  /** <p>inspired by
   * <a href="https://reference.wolfram.com/language/ref/Reals.html">Reals</a>
   * 
   * @return operator that maps a scalar value to instance of {@link RealScalar}
   * so that {@link Scalar#number()} can be invoked. */
  /* package */ ScalarUnaryOperator toReals() {
    return QuantityMagnitude.SI().in(getAxisUnit());
  }

  /* package */ String getUnitString() {
    Unit unit = getAxisUnit();
    return Unit.ONE.equals(unit) ? "" : '[' + PrettyUnit.of(unit) + ']';
  }

  private Unit getAxisUnit() {
    return Objects.isNull(unit) ? Unit.ONE : unit;
  }
}
