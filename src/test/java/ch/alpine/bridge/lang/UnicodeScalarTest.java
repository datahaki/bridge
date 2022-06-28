// code by jph
package ch.alpine.bridge.lang;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

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
    System.out.println(UnicodeScalar.of(RealScalar.of(123456789)));
    System.out.println(UnicodeScalar.of(RealScalar.of(12345678)));
    System.out.println(UnicodeScalar.of(RealScalar.of(1234567)));
    System.out.println(UnicodeScalar.of(RealScalar.of(-123456789)));
    System.out.println(UnicodeScalar.of(RealScalar.of(-12345678)));
    System.out.println(UnicodeScalar.of(RealScalar.of(-1234567)));
  }

  @Test
  void testDouble() {
    System.out.println(UnicodeScalar.of(RealScalar.of(123456789.123123)));
    System.out.println(UnicodeScalar.of(RealScalar.of(12345678.123123)));
    System.out.println(UnicodeScalar.of(RealScalar.of(1234567.123123)));
    System.out.println(UnicodeScalar.of(RealScalar.of(123456789.25)));
    System.out.println(UnicodeScalar.of(RealScalar.of(123456789.25)));
    System.out.println(UnicodeScalar.of(RealScalar.of(-123456789.123123)));
    System.out.println(UnicodeScalar.of(RealScalar.of(-12345678.123123)));
    System.out.println(UnicodeScalar.of(RealScalar.of(-1234567.123123)));
  }
}
