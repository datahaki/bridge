// code by jph
package ch.alpine.bridge.wdog;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ch.alpine.tensor.qty.Quantity;

class HardWatchdogTest {
  @Test
  void testSimple() throws Exception {
    Watchdog watchdog = HardWatchdog.notified(Quantity.of(100, "ms"));
    assertFalse(watchdog.isBarking());
    Thread.sleep(2);
    assertFalse(watchdog.isBarking());
    watchdog.notifyWatchdog();
    assertFalse(watchdog.isBarking());
    Thread.sleep(2);
    assertFalse(watchdog.isBarking());
    watchdog.notifyWatchdog();
    assertFalse(watchdog.isBarking());
    Thread.sleep(102);
    assertTrue(watchdog.isBarking());
  }

  @Test
  void testLazy() throws Exception {
    Watchdog watchdog = HardWatchdog.notified(Quantity.of(0.05, "s"));
    assertFalse(watchdog.isBarking());
    Thread.sleep(70);
    watchdog.notifyWatchdog();
    assertTrue(watchdog.isBarking());
  }
}
