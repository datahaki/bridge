// code by jph
package ch.alpine.java.wdog;

import ch.alpine.tensor.qty.Quantity;
import junit.framework.TestCase;

public class HardWatchdogTest extends TestCase {
  public void testSimple() throws Exception {
    Watchdog watchdog = HardWatchdog.notified(Quantity.of(0.05, "s"));
    assertFalse(watchdog.isBarking());
    Thread.sleep(20);
    assertFalse(watchdog.isBarking());
    watchdog.notifyWatchdog();
    assertFalse(watchdog.isBarking());
    Thread.sleep(20);
    assertFalse(watchdog.isBarking());
    watchdog.notifyWatchdog();
    assertFalse(watchdog.isBarking());
    Thread.sleep(70);
    assertTrue(watchdog.isBarking());
  }

  public void testLazy() throws Exception {
    Watchdog watchdog = HardWatchdog.notified(Quantity.of(0.05, "s"));
    assertFalse(watchdog.isBarking());
    Thread.sleep(70);
    watchdog.notifyWatchdog();
    assertTrue(watchdog.isBarking());
  }
}
