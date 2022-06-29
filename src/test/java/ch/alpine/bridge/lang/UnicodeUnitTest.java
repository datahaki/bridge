// code by jph
package ch.alpine.bridge.lang;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigInteger;

import org.junit.jupiter.api.Test;

import ch.alpine.tensor.DoubleScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.qty.Quantity;
import ch.alpine.tensor.qty.Unit;

class UnicodeUnitTest {
  @Test
  void testOne() {
    String string = Unicode.valueOf(Unit.ONE);
    assertEquals(string, "");
  }

  @Test
  void testOfUnit() {
    assertEquals(Unicode.valueOf(Unit.of("kg*m*s^-2")), "kg*m/s\u00b2");
    assertEquals(Unicode.valueOf(Unit.of("kg*m*s^-2*z^-1")), "kg*m*s\u207b\u00b2*z\u207b\u00b9");
    assertEquals(Unicode.valueOf(Unit.of("kg*m^2")), "kg*m\u00b2");
    assertEquals(Unicode.valueOf(Unit.of("m^-2")), "m\u207b\u00b2");
  }

  @Test
  void testOfUnitChar() {
    assertEquals(Unicode.valueOf(Unit.of("K")), "\u212a");
    assertEquals(Unicode.valueOf(Unit.of("nOhm")), "n\u2126");
    assertEquals(Unicode.valueOf(Unit.of("Ohm")), "\u2126");
    assertEquals(Unicode.valueOf(Unit.of("kOhm")), "k\u2126");
    assertEquals(Unicode.valueOf(Unit.of("MOhm")), "M\u2126");
    assertEquals(Unicode.valueOf(Unit.of("GOhm")), "G\u2126");
  }

  @Test
  void testCurrency() {
    assertEquals(Unicode.valueOf(Unit.of("EUR")), "\u20ac");
    assertEquals(Unicode.valueOf(Unit.of("USD")), "$");
    assertEquals(Unicode.valueOf(Unit.of("GBP")), "\u00a3");
    assertEquals(Unicode.valueOf(Unit.of("JPY")), "\u00a5");
  }

  @Test
  void testIndeterminate() {
    Unicode.valueOf(Quantity.of(DoubleScalar.INDETERMINATE, "degC"));
  }

  @Test
  void testInfty() {
    Unicode.valueOf(Quantity.of(DoubleScalar.POSITIVE_INFINITY, "degC"));
    Unicode.valueOf(Quantity.of(DoubleScalar.NEGATIVE_INFINITY, "degC"));
  }

  @Test
  void testMicro() {
    assertEquals(Unicode.valueOf(Unit.of("us")), "\u03BCs");
    assertEquals(Unicode.valueOf(Unit.of("uF")), "\u03BCF");
    assertEquals(Unicode.valueOf(Unit.of("uOhm")), "\u03BC\u2126");
    assertEquals(Unicode.valueOf(Unit.of("uS")), "\u03BCS");
  }

  @Test
  void testNullFail() {
    assertThrows(Exception.class, () -> Unicode.valueOf((Scalar) null));
    assertThrows(Exception.class, () -> Unicode.valueOf((BigInteger) null));
    assertThrows(Exception.class, () -> Unicode.valueOf((Unit) null));
  }
}
