// code by jph
package ch.alpine.bridge.fig.tic;

import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.sca.Ceiling;
import ch.alpine.tensor.sca.Ramp;
import ch.alpine.tensor.sca.exp.Log10;
import ch.alpine.tensor.sca.pow.Power;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Ticks.html">Ticks</a> */
public class TicksLog extends AbstractTicks {
  public static final AbstractTicks INSTANCE = new TicksLog();

  @Override
  Scalar enhancedStep(Scalar lowerBound) {
    return bumpPreserveUnit(lowerBound);
  }

  @Override
  Scalar bump(Scalar withoutUnit) {
    // here Ramp makes the difference
    return Power.of(10, Ramp.FUNCTION.apply(Ceiling.FUNCTION.apply(Log10.FUNCTION.apply(withoutUnit))));
  }

  @Override
  public Ticks subTicks() {
    return TicksLogSub.INSTANCE;
  }
}
