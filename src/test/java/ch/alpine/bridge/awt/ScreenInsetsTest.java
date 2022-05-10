// code by jph
package ch.alpine.bridge.awt;

import org.junit.jupiter.api.Test;

class ScreenInsetsTest {
  @Test
  public void testSimple() {
    ScreenInsets.of(0, 0);
    ScreenInsets.of(0);
  }
}
