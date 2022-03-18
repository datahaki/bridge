// code by jph
package ch.alpine.javax.swing;

import org.junit.jupiter.api.Test;

public class UIManagerFontTest {
  @Test
  public void testSimple() {
    for (UIManagerFont uiManagerFont : UIManagerFont.values()) {
      uiManagerFont.get();
    }
  }
}
