// code by jph
package ch.alpine.bridge.ref.ann;

import java.util.function.Predicate;

import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Scalars;
import ch.alpine.tensor.api.ScalarUnaryOperator;
import ch.alpine.tensor.chq.FiniteScalarQ;
import ch.alpine.tensor.itp.LinearInterpolation;
import ch.alpine.tensor.qty.CompatibleUnitQ;
import ch.alpine.tensor.qty.Quantity;
import ch.alpine.tensor.qty.QuantityUnit;
import ch.alpine.tensor.qty.Unit;
import ch.alpine.tensor.qty.UnitConvert;
import ch.alpine.tensor.sca.Clip;
import ch.alpine.tensor.sca.Clips;
import ch.alpine.tensor.sca.Round;

public class FieldClips implements Predicate<Scalar> {
  /** @param fieldClip
   * @return
   * @throws Exception if parsing of strings to scalars fails
   * @throws Exception if units of min and max are different
   * @throws Exception if given fieldClip is null */
  public static FieldClips wrap(FieldClip fieldClip) {
    return new FieldClips(fieldClip);
  }

  // ---
  private final Scalar min;
  private final Scalar max;
  private final Clip clip;
  private final Predicate<Scalar> compatible;
  /** operator maps a scalar to a {@link Quantity} with unit as clip.min and clip.max */
  private final ScalarUnaryOperator convert;

  private FieldClips(FieldClip fieldClip) {
    min = Scalars.fromString(fieldClip.min());
    max = Scalars.fromString(fieldClip.max());
    Unit unit = fieldClip.useMinUnit() //
        ? QuantityUnit.of(min)
        : QuantityUnit.of(max);
    compatible = CompatibleUnitQ.SI().with(unit);
    convert = UnitConvert.SI().to(unit);
    clip = Clips.interval(convert.apply(min), convert.apply(max));
  }

  /** @return whether the interval [min, max] has finite width
   * @see FiniteScalarQ */
  public boolean isFinite() {
    return FiniteScalarQ.of(clip.width());
  }

  @Override // from Predicate
  public boolean test(Scalar scalar) {
    return scalar.equals(scalar) // reject if NaN
        && compatible.test(scalar) //
        && clip.isInside(convert.apply(scalar));
  }

  /** @return min scalar as parsed from {@link FieldClip} */
  public Scalar min() {
    return min;
  }

  /** @return max scalar as parsed from {@link FieldClip} */
  public Scalar max() {
    return max;
  }

  /** @param ratio in the unit interval
   * @return */
  public Scalar interp(Scalar ratio) {
    return LinearInterpolation.of(clip).At(ratio);
  }

  public int indexOf(Scalar scalar, int resolution) {
    Scalar rescale = clip.rescale(convert.apply(scalar));
    return Round.FUNCTION.apply(rescale.multiply(RealScalar.of(resolution))).number().intValue();
  }

  /** @return
   * @throws Exception if clip does not define integer range */
  public int getIntegerResolution() {
    return Math.subtractExact( //
        Scalars.intValueExact(clip.max()), //
        Scalars.intValueExact(clip.min()));
  }

  /** @return */
  public Clip clip() {
    return clip;
  }
}
