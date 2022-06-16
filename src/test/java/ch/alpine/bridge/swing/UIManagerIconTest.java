// code by jph
package ch.alpine.bridge.swing;

import org.junit.jupiter.api.Test;

class UIManagerIconTest {
  @Test
  void testSimple() {
    for (UIManagerIcon uiManagerIcon : UIManagerIcon.values()) {
      uiManagerIcon.get();
    }
  }
}
