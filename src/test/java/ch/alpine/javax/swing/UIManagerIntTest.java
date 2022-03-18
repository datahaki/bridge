// code by jph
package ch.alpine.javax.swing;

import org.junit.jupiter.api.Test;

public class UIManagerIntTest {
  @Test
  public void testSimple() {
    for (UIManagerInt uiManagerInt : UIManagerInt.values()) {
      uiManagerInt.getAsInt();
    }
  }
}
