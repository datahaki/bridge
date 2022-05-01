// code by jph
package ch.alpine.javax.swing;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class UIManagerBooleanTest {
  @Test
  public void testSimple() {
    for (UIManagerBoolean uiManagerInt : UIManagerBoolean.values()) {
      uiManagerInt.getAsBoolean();
    }
  }

  @Test
  public void testLength() {
    assertEquals(UIManagerBoolean.values().length, 36);
  }
}
