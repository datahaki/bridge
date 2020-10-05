// code by jph
package ch.ethz.idsc.java.lang;

import ch.ethz.idsc.java.util.AssertFail;
import junit.framework.TestCase;

public class StringRepeatTest extends TestCase {
  public void testSimple() {
    assertEquals(StringRepeat.of("ab", 3), "ababab");
  }

  public void testFailNegative() {
    assertEquals(StringRepeat.of("ab", 0), "");
    AssertFail.of(() -> StringRepeat.of("ab", -1));
  }
}
