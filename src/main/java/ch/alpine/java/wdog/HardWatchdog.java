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
  /** @param timeout_seconds */
  public static Watchdog notified(Scalar timeout) {
    return new HardWatchdog(StaticHelper.toNanos(timeout));
  }

  /***************************************************/
  private final long tolerance_ns;
  private long lastNotify_ns;
  private boolean isBlown = false;

  private HardWatchdog(long tolerance_ns) {
    this.tolerance_ns = tolerance_ns;
    lastNotify_ns = System.nanoTime();
  }

  /** resets timeout counter to zero */
  @Override // from Watchdog
  public void notifyWatchdog() {
    isBarking();
    lastNotify_ns = System.nanoTime();
  }

  /** @return true if timeout counter has ever elapsed the allowed period */
  @Override // from Watchdog
  public boolean isBarking() {
    isBlown |= tolerance_ns < System.nanoTime() - lastNotify_ns;
    return isBlown;
  }
}
