// code by jph
package ch.alpine.javax.swing;

import junit.framework.TestCase;

public class UIManagerStringTest extends TestCase {
  public void testSimple() {
    for (UIManagerString uiManagerString : UIManagerString.values()) {
      uiManagerString.get();
    }
  }
}
