// code by jph
package ch.alpine.bridge.fig;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;

import ch.alpine.bridge.lang.Unicode;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Scalars;
import ch.alpine.tensor.api.ScalarUnaryOperator;
import ch.alpine.tensor.ext.PackageTestAccess;
import ch.alpine.tensor.qty.QuantityMagnitude;
import ch.alpine.tensor.qty.QuantityUnit;
import ch.alpine.tensor.qty.Unit;
import ch.alpine.tensor.qty.UnitConvert;
import ch.alpine.tensor.sca.Clip;
import ch.alpine.tensor.sca.Clips;

/** <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Axis.html">Axis</a> */
public class Axis implements Serializable {
  public enum Type {
    LINEAR, //
    LOGARITHMIC, //
  }

  private String label = "";
  private Unit unit = null;
  private Clip clip = null;
  private Type type = Type.LINEAR;

  public Axis() {
    // ---
  }

  public Axis(Clip clip) {
    setClip(clip);
  }

  /** @param string of axis */
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

  public void setType(Type type) {
    this.type = type;
  }

  public Type getType() {
    return type;
  }

  // ---
  /** @param clip with non-zero width */
  public void setClip(Clip clip) {
    this.clip = clip;
    if (Objects.nonNull(clip)) {
      if (Scalars.isZero(clip.width()))
        System.err.println("empty axis range is not supported");
      if (Objects.isNull(unit))
        setUnit(QuantityUnit.of(clip));
    }
  }

  /** @return
   * @throws Exception if clip was not defined for this axis */
  public Clip getClip() {
    return Objects.requireNonNull(clip);
  }

  public Optional<Clip> getOptionalClip() {
    return Objects.isNull(clip) //
        ? Optional.empty()
        : Optional.of(slash(clip, UnitConvert.SI().to(unit)));
  }

  /** @param clip
   * @param monotonousOperator
   * @return Clip[monotonousOperator[clip.min], monotonousOperator[clip.max]] */
  @PackageTestAccess
  static Clip slash(Clip clip, ScalarUnaryOperator monotonousOperator) {
    return Clips.interval( //
        monotonousOperator.apply(clip.min()), //
        monotonousOperator.apply(clip.max()));
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
    return Unit.ONE.equals(unit) ? "" : '[' + Unicode.valueOf(unit) + ']';
  }

  private Unit getAxisUnit() {
    return Objects.isNull(unit) ? Unit.ONE : unit;
  }
}
