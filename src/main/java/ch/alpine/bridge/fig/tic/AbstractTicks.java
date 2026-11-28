// code by jph
package ch.alpine.bridge.fig.tic;

import java.io.Serializable;
import java.util.stream.Stream;

import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Scalars;
import ch.alpine.tensor.Unprotect;
import ch.alpine.tensor.qty.Quantity;
import ch.alpine.tensor.qty.QuantityUnit;
import ch.alpine.tensor.sca.Ceiling;
import ch.alpine.tensor.sca.Clip;
import ch.alpine.tensor.sca.Sign;

public abstract class AbstractTicks implements Ticks, Serializable {
  @Override
  public final Stream<Scalar> stream(Clip clip, Scalar factor) {
    Scalar step = enhancedStep(clip.length().multiply(factor));
    return Stream.iterate( //
        Ceiling.toMultipleOf(step).apply(clip.min()), //
        Scalars.lessEquals(clip.max()), //
        step::add);
  }

  protected final Scalar bumpPreserveUnit(Scalar lowerBound) {
    Sign.requirePositive(lowerBound);
    Scalar withoutUnit = Unprotect.withoutUnit(lowerBound);
    return Quantity.of(bump(withoutUnit), QuantityUnit.of(lowerBound));
  }

  /** @param lowerBound positive
   * @return value equal or greater than lowerBound */
  abstract Scalar enhancedStep(Scalar lowerBound);

  /** @param withoutUnit positive
   * @return value equal or greater than withoutUnit */
  abstract Scalar bump(Scalar withoutUnit);
}
