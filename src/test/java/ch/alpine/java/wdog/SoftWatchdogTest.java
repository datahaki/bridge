// code by jph
package ch.alpine.java.wdog;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ch.alpine.tensor.qty.Quantity;

public class SoftWatchdogTest {
  @Test
  public void testPacified() throws InterruptedException {
    Watchdog watchdog = SoftWatchdog.notified(Quantity.of(100, "ms")); // 100[ms]
    assertFalse(watchdog.isBarking());
    Thread.sleep(120);
    assertTrue(watchdog.isBarking());
    watchdog.notifyWatchdog();
    assertFalse(watchdog.isBarking());
    Thread.sleep(40);
    assertFalse(watchdog.isBarking());
    Thread.sleep(100);
    assertTrue(watchdog.isBarking());
    watchdog.notifyWatchdog();
    assertFalse(watchdog.isBarking());
  }

  @Test
  public void testBlown() throws InterruptedException {
    Watchdog watchdog = SoftWatchdog.barking(Quantity.of(0.1, "s")); // 100[ms]
    assertTrue(watchdog.isBarking());
    Thread.sleep(10);
    assertTrue(watchdog.isBarking());
    watchdog.notifyWatchdog();
    assertFalse(watchdog.isBarking());
    Thread.sleep(40);
    assertFalse(watchdog.isBarking());
    Thread.sleep(100);
    assertTrue(watchdog.isBarking());
    watchdog.notifyWatchdog();
    assertFalse(watchdog.isBarking());
  }

  @Test
  public void testBarking() throws Exception {
    Watchdog watchdog = SoftWatchdog.barking(Quantity.of(0.01, "s"));
    assertTrue(watchdog.isBarking());
    watchdog.notifyWatchdog();
    assertFalse(watchdog.isBarking());
    Thread.sleep(10);
    assertTrue(watchdog.isBarking());
  }

  @Test
  public void testNotified() throws Exception {
    Watchdog watchdog = SoftWatchdog.notified(Quantity.of(0.01, "s"));
    assertFalse(watchdog.isBarking());
    watchdog.notifyWatchdog();
    assertFalse(watchdog.isBarking());
    Thread.sleep(10);
    assertTrue(watchdog.isBarking());
  }
}
