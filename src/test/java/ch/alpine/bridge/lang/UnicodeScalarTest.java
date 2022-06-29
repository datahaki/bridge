// code by jph
package ch.alpine.bridge.lang;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import ch.alpine.tensor.ComplexScalar;
import ch.alpine.tensor.DoubleScalar;
import ch.alpine.tensor.RationalScalar;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.qty.Quantity;

class UnicodeScalarTest {
  @Test
  void testQuantity() {
    assertEquals(UnicodeScalar.of(RealScalar.of(3)), "3");
    assertEquals(UnicodeScalar.of(Quantity.of(3, "s^2")), "3 s\u00b2");
    assertEquals(UnicodeScalar.of(Quantity.of(3, "s^-2")), "3 s\u207b\u00b2");
    assertEquals(UnicodeScalar.of(Quantity.of(3, "m*s^-2")), "3 m/s\u00b2");
    assertEquals(UnicodeScalar.of(Quantity.of(4, "s^3")), "4 s\u00b3");
    assertEquals(UnicodeScalar.of(Quantity.of(4, "s^-3")), "4 s\u207b\u00b3");
    assertEquals(UnicodeScalar.of(Quantity.of(5, "s^4")), "5 s^4");
    assertEquals(UnicodeScalar.of(Quantity.of(5, "s^-4")), "5 s^-4");
  }

  @Test
  void testDegC() {
    assertEquals(UnicodeScalar.of(Quantity.of(-23, "degC")), "-23 \u2103");
  }

  @Test
  void testInteger() {
    assertEquals(UnicodeScalar.of(RealScalar.of(123456789)), "123\u2009456\u2009789");
    assertEquals(UnicodeScalar.of(RealScalar.of(12345678)), "12\u2009345\u2009678");
    assertEquals(UnicodeScalar.of(RealScalar.of(1234567)), "1\u2009234\u2009567");
    assertEquals(UnicodeScalar.of(RealScalar.of(-123456789)), "-123\u2009456\u2009789");
    assertEquals(UnicodeScalar.of(RealScalar.of(-12345678)), "-12\u2009345\u2009678");
    assertEquals(UnicodeScalar.of(RealScalar.of(-1234567)), "-1\u2009234\u2009567");
  }

  @Test
  void testDouble() {
    assertEquals(UnicodeScalar.of(RealScalar.of(1234567.123123)), "1\u2009234\u2009567.123123");
  }

  @Test
  void testRational() {
    assertEquals(UnicodeScalar.of(RationalScalar.of(1234, 233567)), "1\u2009234 / 233\u2009567");
  }

  @Test
  void testNaN() {
    assertEquals(UnicodeScalar.of(DoubleScalar.INDETERMINATE), "NaN");
  }

  @Test
  void testComplex() {
    assertEquals(UnicodeScalar.of(ComplexScalar.I), "I");
  }
}
