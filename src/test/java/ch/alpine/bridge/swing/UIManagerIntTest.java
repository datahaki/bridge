// code by jph
package ch.alpine.bridge.swing;

import org.junit.jupiter.api.Test;

class UIManagerIntTest {
  @Test
  public void testSimple() {
    for (UIManagerInt uiManagerInt : UIManagerInt.values()) {
      uiManagerInt.getAsInt();
    }
  }
}
