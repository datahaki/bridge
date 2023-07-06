// code by jph
package ch.alpine.bridge.lang;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
}
