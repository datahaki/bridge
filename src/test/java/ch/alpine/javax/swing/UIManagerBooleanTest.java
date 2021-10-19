// code by jph
package ch.alpine.javax.swing;

import junit.framework.TestCase;

public class UIManagerBooleanTest extends TestCase {
  public void testSimple() {
    assertEquals(UIManagerBoolean.values().length, 36);
  }
}
