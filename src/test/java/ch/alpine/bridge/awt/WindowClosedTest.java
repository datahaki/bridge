// code by GRZ Technologies SA, jph
package ch.alpine.bridge.awt;

import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.swing.JFrame;

import org.junit.jupiter.api.Test;

class WindowClosedTest {
  @Test
  void test() {
    assertThrows(Exception.class, () -> WindowClosed.runs(new JFrame(), null));
  }
}
