// code by jph
package ch.alpine.bridge.col;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.awt.Color;

import org.junit.jupiter.api.Test;

class HsluvTest {
  @Test
  void testNegative() {
    Color color1 = Hsluv.of(-0.1, 1, 1, 1);
    Color color2 = Hsluv.of(+1.1, 1, 1, 1);
    assertEquals(color1, color2);
  }

  @Test
  void testFail() {
    assertThrows(Exception.class, () -> Hsluv.of(1 / 0.0, 1, 1, 1));
  }
}
