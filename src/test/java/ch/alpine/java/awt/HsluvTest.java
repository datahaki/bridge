// code by jph
package ch.alpine.java.awt;

import java.awt.Color;

import ch.alpine.java.util.AssertFail;
import junit.framework.TestCase;

public class HsluvTest extends TestCase {
  public void testNegative() {
    Color color1 = Hsluv.of(-0.1, 1, 1, 1);
    Color color2 = Hsluv.of(+1.1, 1, 1, 1);
    assertEquals(color1, color2);
  }

  public void testFail() {
    AssertFail.of(() -> Hsluv.of(1 / 0.0, 1, 1, 1));
  }
}
