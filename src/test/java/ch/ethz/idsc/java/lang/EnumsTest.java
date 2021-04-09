// code by jph
package ch.ethz.idsc.java.lang;

import java.util.Set;

import ch.ethz.idsc.tensor.img.ColorDataGradients;
import junit.framework.TestCase;

public class EnumsTest extends TestCase {
  public void testSimple() {
    Set<ColorDataGradients> set = Enums.setFromMask(ColorDataGradients.class, 2 | 8);
    assertFalse(set.contains(ColorDataGradients.CLASSIC));
    assertTrue(set.contains(ColorDataGradients.HUE));
  }
}
