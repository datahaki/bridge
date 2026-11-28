// code by jph
package ch.alpine.bridge.fig.tic;

import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.api.ScalarUnaryOperator;
import ch.alpine.tensor.red.Max;
import ch.alpine.tensor.sca.Floor;
import ch.alpine.tensor.sca.exp.Log10;
import ch.alpine.tensor.sca.pow.Power;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Ticks.html">Ticks</a> */
class TicksLogSub extends AbstractTicks {
  public static final AbstractTicks INSTANCE = new TicksLogSub();

  @Override
  Scalar enhancedStep(Scalar lowerBound) {
    return bumpPreserveUnit(lowerBound);
  }

  @Override
  Scalar bump(Scalar withoutUnit) {
    // here Ramp makes the difference
    ScalarUnaryOperator suo = scalar -> Max.of(RealScalar.of(-1), scalar);
    // IO.println("withoutUnit "+withoutUnit);
    // IO.println("log10 "+Log10.FUNCTION.apply(withoutUnit));
    // TODO the floor is probably wrong here
    return Power.of(10, suo.apply(Floor.FUNCTION.apply(Log10.FUNCTION.apply(withoutUnit))));
  }

  @Override
  public Ticks subTicks() {
    throw new UnsupportedOperationException();
  }
}
