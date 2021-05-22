// code by jph
package ch.alpine.java.wdog;

import ch.alpine.tensor.Scalar;

/** a soft watchdog recovers from the barking state
 * by a notification from the application layer.
 * 
 * @see Watchdog */
public final class SoftWatchdog implements Watchdog {
  /** @param timeout
   * @return */
  public static Watchdog notified(Scalar timeout) {
    Watchdog watchdog = new SoftWatchdog(StaticHelper.toNanos(timeout));
    watchdog.notifyWatchdog();
    return watchdog;
  }

  /** @param timeout_seconds
   * @return */
  public static Watchdog barking(Scalar timeout) {
    return new SoftWatchdog(StaticHelper.toNanos(timeout));
  }

  /***************************************************/
  private final long tolerance_ns;
  private long lastNotify_ns;

  /** @param timeout_seconds */
  private SoftWatchdog(long tolerance_ns) {
    this.tolerance_ns = tolerance_ns;
    lastNotify_ns = System.nanoTime() - tolerance_ns;
  }

  @Override // from Watchdog
  public void notifyWatchdog() {
    lastNotify_ns = System.nanoTime();
  }

  @Override // from Watchdog
  public boolean isBarking() {
    return tolerance_ns < System.nanoTime() - lastNotify_ns;
  }
}
