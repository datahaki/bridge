// code by jph
package ch.alpine.bridge.gfx;

import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.qty.Quantity;

/** measure length of intervals between invocations of methods
 * {@link #seconds()} and {@link #hertz()} */
class IntervalClock {
  /** started upon construction */
  private long tic = System.nanoTime();

  /** @return period in seconds since last invocation */
  public Scalar seconds() {
    return Quantity.of(elapsed() * 1E-9, "s");
  }

  /** @return reciprocal of period in seconds since last invocation */
  public Scalar hertz() {
    return Quantity.of(1E9 / elapsed(), "Hz");
  }

  private long elapsed() {
    long toc = System.nanoTime();
    long elapsed = toc - tic;
    tic = toc;
    return elapsed;
  }
}
