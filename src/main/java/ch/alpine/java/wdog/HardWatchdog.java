// code by jph
package ch.alpine.java.wdog;

import ch.alpine.tensor.Scalar;

/** implementation of an un-recoverable watchdog
 * 
 * functionality like on a micro controller
 * except that this watchdog does not notify an interrupt
 * but simply sets a flag to true.
 * Once set to true, the flag is never cleared.
 * 
 * @see Watchdog */
public final class HardWatchdog implements Watchdog {
  /** @param timeout with unit compatible with "s", "ms", "us", "ns", ... */
  public static Watchdog notified(Scalar timeout) {
    return new HardWatchdog(StaticHelper.nanos(timeout));
  }

  // ==================================================
  private final long tolerance;
  private long lastNotify;
  /** Once set to true, the flag "isBlown" is never cleared. */
  private boolean isBlown = false;

  /** @param tolerance in nano seconds */
  private HardWatchdog(long tolerance) {
    this.tolerance = tolerance;
    lastNotify = System.nanoTime();
  }

  /** resets timeout counter to zero */
  @Override // from Watchdog
  public void notifyWatchdog() {
    isBarking();
    lastNotify = System.nanoTime();
  }

  /** @return true if timeout counter has ever elapsed the allowed period */
  @Override // from Watchdog
  public boolean isBarking() {
    if (!isBlown) // only evaluate if necessary
      isBlown = tolerance < System.nanoTime() - lastNotify;
    return isBlown;
  }
}
