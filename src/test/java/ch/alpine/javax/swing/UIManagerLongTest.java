// code by jph
package ch.alpine.javax.swing;

import org.junit.jupiter.api.Test;

public class UIManagerLongTest {
  @Test
  public void testSimple() {
    for (UIManagerLong uiManagerLong : UIManagerLong.values()) {
      uiManagerLong.getAsLong();
    }
  }
}
