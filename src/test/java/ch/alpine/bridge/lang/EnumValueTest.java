// code by jph
package ch.alpine.bridge.lang;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import ch.alpine.tensor.mat.re.Pivots;
import ch.alpine.tensor.sca.win.WindowFunctions;

class EnumValueTest {
  @Test
  void testPivots() {
    assertEquals(EnumValue.match(Pivots.class, "first"), Pivots.FIRST_NON_ZERO);
    assertEquals(EnumValue.match(Pivots.class, "argabs"), Pivots.ARGMAX_ABS);
  }

  @Test
  void testWindowFunctions() {
    assertEquals(EnumValue.match(WindowFunctions.class, "manharris"), WindowFunctions.BLACKMAN_HARRIS);
    assertEquals(EnumValue.match(WindowFunctions.class, "drchlt"), WindowFunctions.DIRICHLET);
    assertEquals(EnumValue.match(WindowFunctions.class, "nttl"), WindowFunctions.NUTTALL);
  }

  @Test
  void testEmptyFail() {
    assertThrows(Exception.class, () -> EnumValue.match(EnumValue.class, ""));
  }

  @Test
  void testNullFail() {
    assertThrows(Exception.class, () -> EnumValue.match(null, "a"));
    assertThrows(Exception.class, () -> EnumValue.match(WindowFunctions.class, null));
  }

  @Test
  void testCycle() {
    assertEquals(EnumValue.cycle(WindowFunctions.TUKEY, 3), WindowFunctions.BARTLETT_HANN);
    assertEquals(EnumValue.cycle(WindowFunctions.TUKEY, -3), WindowFunctions.NUTTALL);
  }

  @Test
  void testFromOrdinal() {
    assertEquals(EnumValue.fromOrdinal(WindowFunctions.class, 5), WindowFunctions.BOHMAN);
    assertThrows(Exception.class, () -> EnumValue.fromOrdinal(WindowFunctions.class, -1));
    assertThrows(Exception.class, () -> EnumValue.fromOrdinal(WindowFunctions.class, 1000));
  }
}
