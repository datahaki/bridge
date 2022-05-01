// code by jph
package ch.alpine.javax.swing;

import org.junit.jupiter.api.Test;

class UIManagerInsetsTest {
  @Test
  public void testSimple() {
    for (UIManagerInsets uiManagerInsets : UIManagerInsets.values()) {
      uiManagerInsets.get();
    }
  }
}
