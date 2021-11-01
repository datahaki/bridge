// code by jph
package ch.alpine.javax.swing;

import junit.framework.TestCase;

public class UIManagerLongTest extends TestCase {
  public void testSimple() {
    for (UIManagerLong uiManagerLong : UIManagerLong.values()) {
      uiManagerLong.getAsLong();
    }
  }
}
