// code by jph
package ch.alpine.javax.swing;

import org.junit.jupiter.api.Test;

public class UIManagerIconTest {
  @Test
  public void testSimple() {
    for (UIManagerIcon uiManagerIcon : UIManagerIcon.values()) {
      uiManagerIcon.get();
    }
  }
}
