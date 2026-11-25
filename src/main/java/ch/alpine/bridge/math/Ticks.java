// code by jph
package ch.alpine.bridge.math;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import ch.alpine.tensor.Rational;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Scalars;
import ch.alpine.tensor.Unprotect;
import ch.alpine.tensor.qty.Quantity;
import ch.alpine.tensor.qty.QuantityUnit;
import ch.alpine.tensor.sca.Ceiling;
import ch.alpine.tensor.sca.Clip;
import ch.alpine.tensor.sca.Sign;
import ch.alpine.tensor.sca.exp.Log10;
import ch.alpine.tensor.sca.pow.Power;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Ticks.html">Ticks</a> */
public enum Ticks {
  DENSE(5, 4, 2);

  private final List<Scalar> RATIOS;

  Ticks(int... i) {
    RATIOS = IntStream.of(i).mapToObj(k -> Rational.of(1, k)).toList();
  }

  /** @param clip
   * @param factor e.g. 1) fontSize / plotHeight, or 2) Rational.of(50, dimension.width) ...
   * @return */
  public Stream<Scalar> stream(Clip clip, Scalar factor) {
    Scalar step = getDecimalStep(clip.length().multiply(factor));
    return Stream.iterate( //
        Ceiling.toMultipleOf(step).apply(clip.min()), //
        Scalars.lessEquals(clip.max()), //
        step::add);
  }

  /** @param scalar positive
   * @return */
  private Scalar getDecimalStep(Scalar scalar) {
    Sign.requirePositive(scalar);
    Scalar decStep = Quantity.of( //
        Power.of(10, Ceiling.FUNCTION.apply(Log10.FUNCTION.apply(Unprotect.withoutUnit(scalar)))), //
        QuantityUnit.of(scalar));
    return RATIOS.stream() //
        .map(decStep::multiply) //
        .filter(Scalars.greaterEquals(scalar)) //
        .findFirst() //
        .orElse(decStep);
  }
}
