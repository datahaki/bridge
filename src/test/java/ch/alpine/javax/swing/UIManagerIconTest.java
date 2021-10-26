// code by jph
package ch.alpine.javax.swing;

import junit.framework.TestCase;

public class UIManagerIconTest extends TestCase {
  public void testSimple() {
    for (UIManagerIcon uiManagerIcon : UIManagerIcon.values()) {
      uiManagerIcon.get();
    }
  }
}
