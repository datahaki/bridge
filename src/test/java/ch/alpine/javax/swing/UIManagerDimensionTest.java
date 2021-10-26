// code by jph
package ch.alpine.javax.swing;

import junit.framework.TestCase;

public class UIManagerDimensionTest extends TestCase {
  public void testSimple() {
    for (UIManagerDimension uiManagerDimension : UIManagerDimension.values()) {
      uiManagerDimension.get();
    }
  }
}
