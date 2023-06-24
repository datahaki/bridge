// code by jph
package ch.alpine.bridge.lang;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ch.alpine.tensor.qty.Quantity;

class SITest {
  @Test
  void testConvert() {
    assertEquals(SI.PERCENT.convert(Quantity.of(3, "")), Quantity.of(300, "%"));
    assertEquals(SI.METERS_PER_SECOND.convert(Quantity.of(-90, "km*h^-1")), Quantity.of(-25, "m*s^-1"));
  }

  @Test
  void testMagnitude() {
    assertEquals(SI.SECONDS.longValue(Quantity.of(3, "min")), 3 * 60);
    assertEquals(SI.SECONDS.longValueExact(Quantity.of(3, "min")), 3 * 60);
    assertEquals(SI.SECONDS.longValue(Quantity.of(1.000001, "min")), 60);
    assertEquals(SI.SECONDS.longValue(Quantity.of(1 - 1E-6, "min")), 60);
    assertEquals(SI.SECONDS.intValue(Quantity.of(1, "min")), 60);
    assertEquals(SI.SECONDS.intValueExact(Quantity.of(1, "min")), 60);
    assertEquals(SI.SECONDS.intValue(Quantity.of(1.000001, "min")), 60);
    assertEquals(SI.SECONDS.intValue(Quantity.of(1 - 1E-6, "min")), 60);
    assertEquals(SI.SECONDS.byteValue(Quantity.of(1 - 1E-6, "min")), 60);
    assertEquals(SI.SECONDS.byteValue(Quantity.of(-1 + 1E-6, "min")), -60);
    assertEquals(SI.SECONDS.byteValue(Quantity.of(-1 - 1E-6, "min")), -60);
  }

  @Test
  void testMagnitudeDecimal() {
    assertEquals(SI.SECONDS.floatValue(Quantity.of(1, "min")), 60f);
    assertEquals(SI.SECONDS.doubleValue(Quantity.of(1, "min")), 60.0);
  }

  @Test
  void testToString() {
    assertTrue(SI.SECONDS.toString().contains("[s]"));
  }
}
