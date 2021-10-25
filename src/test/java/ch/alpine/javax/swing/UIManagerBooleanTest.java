// code by jph
package ch.alpine.javax.swing;

import junit.framework.TestCase;

public class UIManagerBooleanTest extends TestCase {
  public void testSimple() {
    for (UIManagerBoolean uiManagerInt : UIManagerBoolean.values()) {
      uiManagerInt.getAsBoolean();
    }
  }

  public void testLength() {
    assertEquals(UIManagerBoolean.values().length, 36);
  }
}
