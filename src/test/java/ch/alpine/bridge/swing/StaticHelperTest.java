// code by jph
package ch.alpine.bridge.swing;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Color;

import org.junit.jupiter.api.Test;

class StaticHelperTest {
  @Test
  public void testSimple() {
    assertEquals(StaticHelper.alpha064(Color.WHITE).getAlpha(), 64);
    assertEquals(StaticHelper.alpha128(Color.WHITE).getAlpha(), 128);
  }

  @Test
  public void testVisibility() {
    assertEquals(StaticHelper.class.getModifiers() & 1, 0);
  }
}
