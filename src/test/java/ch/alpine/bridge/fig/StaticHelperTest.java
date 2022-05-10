// code by jph
package ch.alpine.bridge.fig;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class StaticHelperTest {
  @Test
  public void testSimple() {
    assertEquals(StaticHelper.class.getModifiers() & 1, 0);
  }
}
