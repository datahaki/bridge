// code by jph
package ch.ethz.idsc.java.lang;

import java.util.Set;

import ch.ethz.idsc.java.util.AssertFail;
import ch.ethz.idsc.tensor.img.ColorDataGradients;
import ch.ethz.idsc.tensor.mat.re.Pivot;
import ch.ethz.idsc.tensor.mat.re.Pivots;
import junit.framework.TestCase;

public class EnumsTest extends TestCase {
  public void testSimple() {
    Set<ColorDataGradients> set = Enums.setFromMask(ColorDataGradients.class, 2 | 8);
    assertFalse(set.contains(ColorDataGradients.CLASSIC));
    assertTrue(set.contains(ColorDataGradients.HUE));
    assertFalse(set.contains(ColorDataGradients.HSLUV));
    assertTrue(set.contains(ColorDataGradients.SUNSET));
  }

  public void testAdvance() {
    Class<? extends Pivots> cls = Pivots.ARGMAX_ABS.getClass();
    assertTrue(cls.isAnonymousClass());
    Class<?> enc = cls.getEnclosingClass();
    assertFalse(enc.isAnonymousClass());
    assertTrue(Enum.class.isAssignableFrom(enc));
    Pivot pivot = Enums.increment(Pivots.class, Pivots.ARGMAX_ABS);
    assertEquals(pivot, Pivots.FIRST_NON_ZERO);
    AssertFail.of(() -> Enums.increment(Pivots.class, Pivots.FIRST_NON_ZERO));
  }
}
