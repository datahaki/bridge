// code by jph
package ch.alpine.java.fig;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class StaticHelperTest {
  @Test
  public void testSimple() {
    assertEquals(StaticHelper.class.getModifiers() & 1, 0);
  }
}
