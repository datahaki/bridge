// code by jph
package ch.alpine.bridge.swing;

import org.junit.jupiter.api.Test;

class UIManagerStringTest {
  @Test
  public void testSimple() {
    for (UIManagerString uiManagerString : UIManagerString.values()) {
      uiManagerString.get();
    }
  }
}
