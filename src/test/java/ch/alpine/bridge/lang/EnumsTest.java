// code by jph
package ch.alpine.bridge.lang;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;

import ch.alpine.tensor.img.ColorDataGradients;
import ch.alpine.tensor.mat.re.Pivot;
import ch.alpine.tensor.mat.re.Pivots;

class EnumsTest {
  private static enum NestedEnum {
    A,
    B,
    C;
  }

  @Test
  void testSimple() {
    Set<ColorDataGradients> set = Enums.setFromMask(ColorDataGradients.class, 2 | 8);
    assertFalse(set.contains(ColorDataGradients.CLASSIC));
    assertTrue(set.contains(ColorDataGradients.HUE));
    assertFalse(set.contains(ColorDataGradients.HSLUV));
    assertTrue(set.contains(ColorDataGradients.SUNSET));
  }

  @Test
  void testAdvance() {
    Class<? extends Pivots> cls = Pivots.ARGMAX_ABS.getClass();
    assertTrue(cls.isAnonymousClass());
    Class<?> enc = cls.getEnclosingClass();
    assertFalse(enc.isAnonymousClass());
    assertTrue(Enum.class.isAssignableFrom(enc));
    Pivot pivot = Enums.increment(Pivots.ARGMAX_ABS);
    assertEquals(pivot, Pivots.FIRST_NON_ZERO);
    assertThrows(Exception.class, () -> Enums.increment(Pivots.FIRST_NON_ZERO));
  }

  @Test
  void testCycle() {
    Pivot pivot = Enums.cycle(Enums.cycle(Pivots.ARGMAX_ABS));
    assertEquals(pivot, Pivots.ARGMAX_ABS);
  }

  @Test
  void testNested() {
    assertEquals(Enums.increment(NestedEnum.A), NestedEnum.B);
  }
}