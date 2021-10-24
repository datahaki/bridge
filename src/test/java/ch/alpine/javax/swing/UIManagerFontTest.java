// code by jph
package ch.alpine.javax.swing;

import junit.framework.TestCase;

public class UIManagerFontTest extends TestCase {
  public void testSimple() {
    for (UIManagerFont uiManagerFont : UIManagerFont.values()) {
      uiManagerFont.get();
    }
  }
}
