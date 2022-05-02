// code by jph
package ch.alpine.bridge.wdog;

import ch.alpine.tensor.Scalar;

/** a soft watchdog recovers from the barking state
 * by a notification from the application layer.
 * 
 * @see Watchdog */
public final class SoftWatchdog implements Watchdog {
  /** @param timeout with unit compatible with "s", "ms", "us", "ns", ...
   * @return */
  public static Watchdog notified(Scalar timeout) {
    Watchdog watchdog = new SoftWatchdog(StaticHelper.nanos(timeout));
    watchdog.notifyWatchdog();
    return watchdog;
  }

  /** @param timeout with unit compatible with "s", "ms", "us", "ns", ...
   * @return */
  public static Watchdog barking(Scalar timeout) {
    return new SoftWatchdog(StaticHelper.nanos(timeout));
  }

  // ---
  private final long tolerance;
  private long lastNotify;

  /** @param tolerance */
  private SoftWatchdog(long tolerance) {
    this.tolerance = tolerance;
    lastNotify = System.nanoTime() - tolerance;
  }

  @Override // from Watchdog
  public void notifyWatchdog() {
    lastNotify = System.nanoTime();
  }

  @Override // from Watchdog
  public boolean isBarking() {
    return tolerance < System.nanoTime() - lastNotify;
  }
}
