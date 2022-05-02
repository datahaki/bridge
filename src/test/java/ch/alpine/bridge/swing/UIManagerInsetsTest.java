// code by jph
package ch.alpine.bridge.swing;

import org.junit.jupiter.api.Test;

class UIManagerInsetsTest {
  @Test
  public void testSimple() {
    for (UIManagerInsets uiManagerInsets : UIManagerInsets.values()) {
      uiManagerInsets.get();
    }
  }
}
