// code by jph
package ch.ethz.idsc.java.lang;

import junit.framework.TestCase;

public class StringRepeatTest extends TestCase {
  public void testSimple() {
    assertEquals(StringRepeat.of("ab", 3), "ababab");
  }
}
