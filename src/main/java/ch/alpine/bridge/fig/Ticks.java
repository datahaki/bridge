// code by jph
package ch.alpine.bridge.fig;

import java.util.Arrays;
import java.util.stream.Stream;

import ch.alpine.bridge.lang.UnicodeString;
import ch.alpine.tensor.Rational;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Scalars;
import ch.alpine.tensor.Unprotect;
import ch.alpine.tensor.chq.IntegerQ;
import ch.alpine.tensor.qty.Quantity;
import ch.alpine.tensor.qty.QuantityUnit;
import ch.alpine.tensor.sca.Ceiling;
import ch.alpine.tensor.sca.Clip;
import ch.alpine.tensor.sca.N;
import ch.alpine.tensor.sca.Sign;
import ch.alpine.tensor.sca.exp.Log10;
import ch.alpine.tensor.sca.pow.Power;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Ticks.html">Ticks</a> */
public enum Ticks {
  ;
  private static final Scalar[] RATIOS = { //
      Rational.of(1, 5), //
      Rational.of(1, 2) };

  /** @param clip
   * @param factor e.g. 1) fontSize / plotHeight, or 2) Rational.of(50, dimension.width) ...
   * @return */
  public static Stream<Scalar> stream(Clip clip, Scalar factor) {
    Scalar step = getDecimalStep(clip.width().multiply(factor));
    return Stream.iterate(Ceiling.toMultipleOf(step).apply(clip.min()), //
        Scalars.lessEquals(clip.max()), //
        step::add);
  }

  /** @param scalar positive
   * @return */
  // TODO reduce visibility
  public static Scalar getDecimalStep(Scalar scalar) {
    Sign.requirePositive(scalar);
    Scalar decStep = Quantity.of( //
        Power.of(10, Ceiling.FUNCTION.apply(Log10.FUNCTION.apply(Unprotect.withoutUnit(scalar)))), //
        QuantityUnit.of(scalar));
    return Arrays.stream(RATIOS) //
        .map(decStep::multiply) //
        .filter(Scalars.greaterEquals(scalar)) //
        .findFirst() //
        .orElse(decStep);
  }

  public static String format(Scalar value) {
    Scalar display = Unprotect.withoutUnit(value);
    Scalar scalar = IntegerQ.of(display) //
        ? display
        : N.DOUBLE.apply(display);
    return UnicodeString.of(scalar);
  }
}
