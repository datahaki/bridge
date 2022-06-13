// code by jph
package ch.alpine.bridge.swing;

import org.junit.jupiter.api.Test;

class UIManagerStringTest {
  @Test
  void testSimple() {
    for (UIManagerString uiManagerString : UIManagerString.values()) {
      uiManagerString.get();
    }
  }
}
