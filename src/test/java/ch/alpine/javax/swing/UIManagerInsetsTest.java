// code by jph
package ch.alpine.javax.swing;

import junit.framework.TestCase;

public class UIManagerInsetsTest extends TestCase {
  public void testSimple() {
    for (UIManagerInsets uiManagerInsets : UIManagerInsets.values()) {
      uiManagerInsets.get();
    }
  }
}
