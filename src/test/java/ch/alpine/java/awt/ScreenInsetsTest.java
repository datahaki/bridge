// code by jph
package ch.alpine.java.awt;

import org.junit.jupiter.api.Test;

public class ScreenInsetsTest {
  @Test
  public void testSimple() {
    ScreenInsets.of(0, 0);
    ScreenInsets.of(0);
  }
}
