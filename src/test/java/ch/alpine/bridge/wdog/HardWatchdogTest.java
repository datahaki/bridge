// code by jph
package ch.alpine.bridge.wdog;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ch.alpine.tensor.qty.Quantity;

class HardWatchdogTest {
  @Test
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

  @Test
  public void testLazy() throws Exception {
    Watchdog watchdog = HardWatchdog.notified(Quantity.of(0.05, "s"));
    assertFalse(watchdog.isBarking());
    Thread.sleep(70);
    watchdog.notifyWatchdog();
    assertTrue(watchdog.isBarking());
  }
}
