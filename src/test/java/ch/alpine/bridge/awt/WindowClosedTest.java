// code by GRZ Technologies SA, jph
package ch.alpine.bridge.awt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Duration;

import javax.swing.JFrame;

import org.junit.jupiter.api.Test;

import ch.alpine.tensor.ext.Int;

class WindowClosedTest {
  @Test
  void test() throws InterruptedException {
    JFrame jFrame = new JFrame();
    Int i = new Int();
    WindowClosed.runs(jFrame, i::getAndIncrement);
    jFrame.setVisible(true);
    jFrame.dispose();
    Thread.sleep(Duration.ofMillis(100));
    assertEquals(i.getAndIncrement(), 1);
  }

  @Test
  void testFail() {
    assertThrows(Exception.class, () -> WindowClosed.runs(new JFrame(), null));
  }
}
