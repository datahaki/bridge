// code by jph
package ch.alpine.javax.swing;

import junit.framework.TestCase;

public class UIManagerIntTest extends TestCase {
  public void testSimple() {
    for (UIManagerInt uiManagerInt : UIManagerInt.values()) {
      uiManagerInt.getAsInt();
    }
  }
}
