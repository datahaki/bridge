// code by jph
package ch.alpine.bridge.swing;

import org.junit.jupiter.api.Test;

class UIManagerFontTest {
  @Test
  void testSimple() {
    for (UIManagerFont uiManagerFont : UIManagerFont.values()) {
      uiManagerFont.get();
    }
  }
}
