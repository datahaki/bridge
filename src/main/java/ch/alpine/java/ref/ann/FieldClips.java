// code by jph
package ch.alpine.java.ref.ann;

import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Scalars;
import ch.alpine.tensor.qty.Quantity;
import ch.alpine.tensor.qty.UnitSystem;
import ch.alpine.tensor.red.Max;
import ch.alpine.tensor.red.Min;
import ch.alpine.tensor.sca.Clip;
import ch.alpine.tensor.sca.Clips;
import ch.alpine.tensor.sca.N;

/** interprets specification in {@link FieldClip} to {@link Clip} */
public enum FieldClips {
  ;
  /** @param fieldClip
   * @return clip with min and max {@link Quantity} in SI-unit
   * @throws Exception if parsing of strings to scalars fails
   * @throws Exception if units of min and max are incompatible */
  public static Clip of(FieldClip fieldClip) {
    return of( //
        Scalars.fromString(fieldClip.min()), //
        Scalars.fromString(fieldClip.max()));
  }

  /** Example: it was discovered that due to floating point imprecision
   * 20[L*min^-1] < 20.0[L*min^-1] when values are converted to SI unit "m^3*s^-1"
   * although equality is expected. As a remedy therefore the smaller/larger of
   * the exact and numeric value of min/max is taken. */
  public static Clip of(Scalar min, Scalar max) {
    return Clips.interval( //
        Min.of(UnitSystem.SI().apply(min), UnitSystem.SI().apply(N.DOUBLE.apply(min))), //
        Max.of(UnitSystem.SI().apply(max), UnitSystem.SI().apply(N.DOUBLE.apply(max))));
  }
}
