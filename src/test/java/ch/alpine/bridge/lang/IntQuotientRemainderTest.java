package ch.alpine.bridge.lang;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class IntQuotientRemainderTest {
  @Test
  void testMathematica() {
    IntQuotientRemainder iqr = IntQuotientRemainder.from(7, 2);
    assertEquals(iqr.div(), 3);
    assertEquals(iqr.rem(), 1);
  }

  @Test
  void testMask() {
    IntQuotientRemainder iqr = IntQuotientRemainder.from(256 + 255, 256);
    assertEquals(iqr.div(), 1);
    assertEquals(iqr.rem(), 255);
  }
}
