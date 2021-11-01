// code by jph
package ch.alpine.javax.swing;

import junit.framework.TestCase;

public class UIManagerBorderTest extends TestCase {
  public void testSimple() {
    for (UIManagerBorder uiManagerBorder : UIManagerBorder.values()) {
      uiManagerBorder.get();
    }
  }
}
