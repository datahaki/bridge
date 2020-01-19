// code by jph
package ch.ethz.idsc.java.awt;

import java.awt.Insets;

import junit.framework.TestCase;

public class ScreenInsetsTest extends TestCase {
  public void testSimple() {
    Insets insets = ScreenInsets.of(0, 20);
    if (insets != null)
      System.out.println(insets);
  }
}
