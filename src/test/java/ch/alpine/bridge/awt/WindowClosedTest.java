// code by GRZ Technologies SA, jph
package ch.alpine.bridge.awt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.JFrame;

import org.junit.jupiter.api.Test;

class WindowClosedTest {
  @Test
  void test() throws InterruptedException {
    JFrame jFrame = new JFrame();
    AtomicInteger atomicInteger = new AtomicInteger();
    WindowClosed.runs(jFrame, atomicInteger::getAndIncrement);
    jFrame.setVisible(true);
    jFrame.dispose();
    Thread.sleep(100);
    assertEquals(atomicInteger.get(), 1);
  }

  @Test
  void testFail() {
    assertThrows(Exception.class, () -> WindowClosed.runs(new JFrame(), null));
  }
}
