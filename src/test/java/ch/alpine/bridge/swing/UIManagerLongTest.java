// code by jph
package ch.alpine.bridge.swing;

import org.junit.jupiter.api.Test;

class UIManagerLongTest {
  @Test
  public void testSimple() {
    for (UIManagerLong uiManagerLong : UIManagerLong.values()) {
      uiManagerLong.getAsLong();
    }
  }
}
