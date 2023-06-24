// code by jph
package ch.alpine.bridge.lang;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.qty.Quantity;

class SITest {
  @Test
  void testConvert() {
    assertEquals(SI.PERCENT.convert(Quantity.of(3, "")), Quantity.of(300, "%"));
  }

  @Test
  void testMagnitude() {
    assertEquals(SI.SECONDS.magnitude(Quantity.of(3, "min")), RealScalar.of(3 * 60));
    assertEquals(SI.SECONDS.intValue(Quantity.of(1, "min")), 60);
    assertEquals(SI.SECONDS.intValue(Quantity.of(1.000001, "min")), 60);
    assertEquals(SI.SECONDS.intValue(Quantity.of(1-1E-6, "min")), 60);
    assertEquals(SI.SECONDS.longValue(Quantity.of(1.000001, "min")), 60);
    assertEquals(SI.SECONDS.longValue(Quantity.of(1-1E-6, "min")), 60);
    assertEquals(SI.SECONDS.floatValue(Quantity.of(1, "min")), 60f);
    assertEquals(SI.SECONDS.doubleValue(Quantity.of(1, "min")), 60.0);
  }
}
