// code by jph
package ch.alpine.java.fig;

import junit.framework.TestCase;

public class StaticHelperTest extends TestCase {
  public void testSimple() {
    assertEquals(StaticHelper.class.getModifiers() & 1, 0);
  }
}
