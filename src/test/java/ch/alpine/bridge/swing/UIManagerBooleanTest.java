// code by jph
package ch.alpine.bridge.swing;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class UIManagerBooleanTest {
  @Test
  void testSimple() {
    for (UIManagerBoolean uiManagerInt : UIManagerBoolean.values()) {
      uiManagerInt.getAsBoolean();
    }
  }

  @Test
  void testLength() {
    assertEquals(UIManagerBoolean.values().length, 36);
  }
}
