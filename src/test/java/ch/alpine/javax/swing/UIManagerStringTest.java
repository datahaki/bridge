// code by jph
package ch.alpine.javax.swing;

import org.junit.jupiter.api.Test;

public class UIManagerStringTest {
  @Test
  public void testSimple() {
    for (UIManagerString uiManagerString : UIManagerString.values()) {
      uiManagerString.get();
    }
  }
}
