// code by jph
package ch.alpine.java.awt;

import java.awt.Color;

import junit.framework.TestCase;

public class StaticHelperTest extends TestCase {
  public void testSimple() {
    assertEquals(StaticHelper.alpha064(Color.WHITE).getAlpha(), 64);
    assertEquals(StaticHelper.alpha128(Color.WHITE).getAlpha(), 128);
  }
}
