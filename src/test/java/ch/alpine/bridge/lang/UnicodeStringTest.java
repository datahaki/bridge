// code by jph
package ch.alpine.bridge.lang;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import ch.alpine.tensor.ComplexScalar;
import ch.alpine.tensor.DoubleScalar;
import ch.alpine.tensor.RationalScalar;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.qty.DateTime;
import ch.alpine.tensor.qty.Quantity;

class UnicodeStringTest {
  // private static final String T = "\u2009";
  private static final char D = '\u2215';

  @Test
  void testQuantity() {
    assertEquals(UnicodeString.of(RealScalar.of(3)), "3");
    assertEquals(UnicodeString.of(Quantity.of(3, "s^2")), "3 s\u00b2");
    assertEquals(UnicodeString.of(Quantity.of(3, "s^-2")), "3 s\u207b\u00b2");
    assertEquals(UnicodeString.of(Quantity.of(3, "m*s^-2")), "3 m" + D + "s\u00b2");
    assertEquals(UnicodeString.of(Quantity.of(4, "s^3")), "4 s\u00b3");
    assertEquals(UnicodeString.of(Quantity.of(4, "s^-3")), "4 s\u207b\u00b3");
    assertEquals(UnicodeString.of(Quantity.of(5, "s^4")), "5 s^4");
    assertEquals(UnicodeString.of(Quantity.of(5, "s^-4")), "5 s^-4");
  }

  @Test
  void testDegC() {
    assertEquals(UnicodeString.of(Quantity.of(-23, "degC")), "-23 \u2103");
  }

  @Test
  void testScalarInteger() {
    assertEquals(UnicodeString.of(RealScalar.of(123456789)), "123\u2009456\u2009789");
    assertEquals(UnicodeString.of(RealScalar.of(12345678)), "12\u2009345\u2009678");
    assertEquals(UnicodeString.of(RealScalar.of(1234567)), "1\u2009234\u2009567");
    assertEquals(UnicodeString.of(RealScalar.of(-123456789)), "-123\u2009456\u2009789");
    assertEquals(UnicodeString.of(RealScalar.of(-12345678)), "-12\u2009345\u2009678");
    assertEquals(UnicodeString.of(RealScalar.of(-1234567)), "-1\u2009234\u2009567");
  }

  @Test
  void testInteger() {
    assertEquals(UnicodeString.of(123456789), "123\u2009456\u2009789");
    assertEquals(UnicodeString.of(12345678), "12\u2009345\u2009678");
    assertEquals(UnicodeString.of(1234567), "1\u2009234\u2009567");
    assertEquals(UnicodeString.of(-123456789), "-123\u2009456\u2009789");
    assertEquals(UnicodeString.of(-12345678), "-12\u2009345\u2009678");
    assertEquals(UnicodeString.of(-1234567), "-1\u2009234\u2009567");
  }

  @Test
  void testDouble() {
    assertEquals(UnicodeString.of(RealScalar.of(1234567.123123)), "1\u2009234\u2009567.123123");
  }

  @Test
  void testRational() {
    assertEquals(UnicodeString.of(RationalScalar.of(1234, 233567)), "1\u2009234 / 233\u2009567");
  }

  @Test
  void testDateTime() {
    DateTime dateTime = DateTime.of(2022, 1, 2, 3, 4, 5);
    assertEquals(UnicodeString.of(dateTime), "2022-01-02T03:04:05");
  }

  @Test
  void testNaN() {
    assertEquals(UnicodeString.of(DoubleScalar.INDETERMINATE), "NaN");
  }

  @Test
  void testComplex() {
    assertEquals(UnicodeString.of(ComplexScalar.I), "I");
  }
}
