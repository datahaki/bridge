// code by jph
package ch.alpine.bridge.swing;

import org.junit.jupiter.api.Test;

class UIManagerBorderTest {
  @Test
  public void testSimple() {
    for (UIManagerBorder uiManagerBorder : UIManagerBorder.values()) {
      uiManagerBorder.get();
    }
  }
}