// code by jph
package ch.ethz.idsc.java.lang;

import junit.framework.TestCase;

public class StringRepeatTest extends TestCase {
  public void testSimple() {
    assertEquals(StringRepeat.of("ab", 3), "ababab");
  }

  public void testFailNegative() {
    assertEquals(StringRepeat.of("ab", 0), "");
    try {
      StringRepeat.of("ab", -1);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
