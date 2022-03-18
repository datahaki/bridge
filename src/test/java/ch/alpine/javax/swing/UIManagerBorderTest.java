// code by jph
package ch.alpine.javax.swing;

import org.junit.jupiter.api.Test;

public class UIManagerBorderTest {
  @Test
  public void testSimple() {
    for (UIManagerBorder uiManagerBorder : UIManagerBorder.values()) {
      uiManagerBorder.get();
    }
  }
}
