// code by jph
package ch.alpine.bridge.fig.tic;

import java.util.List;
import java.util.stream.IntStream;

import ch.alpine.tensor.Rational;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Scalars;
import ch.alpine.tensor.sca.Ceiling;
import ch.alpine.tensor.sca.exp.Log10;
import ch.alpine.tensor.sca.pow.Power;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Ticks.html">Ticks</a> */
public class TicksDec extends AbstractTicks {
  public static final AbstractTicks DENSE = new TicksDec(5, 4, 2);
  // ---
  private final List<Scalar> ratios;

  /** @param denoms in decreasing order */
  public TicksDec(int... denoms) {
    ratios = IntStream.of(denoms) //
        .mapToObj(k -> Rational.of(1, k)) //
        .toList();
  }

  @Override
  Scalar enhancedStep(Scalar lowerBound) {
    Scalar decStep = bumpPreserveUnit(lowerBound);
    return ratios.stream() //
        .map(decStep::multiply) //
        .filter(Scalars.greaterEquals(lowerBound)) //
        .findFirst() //
        .orElse(decStep);
  }

  @Override
  Scalar bump(Scalar withoutUnit) {
    return Power.of(10, Ceiling.FUNCTION.apply(Log10.FUNCTION.apply(withoutUnit)));
  }

  @Override
  public Ticks subTicks() {
    return this;
  }
}
