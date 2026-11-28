// code by jph
package ch.alpine.bridge.fig.tic;

import java.util.stream.Stream;

import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.sca.Clip;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Ticks.html">Ticks</a> */
public interface Ticks {
  /** @param clip
   * @param factor e.g. 1) fontSize / plotHeight, or 2) Rational.of(50, dimension.width) ...
   * @return */
  Stream<Scalar> stream(Clip clip, Scalar factor);

  Ticks subTicks();
}
