// code by jph
package ch.alpine.bridge.lang;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import ch.alpine.tensor.DoubleScalar;
import ch.alpine.tensor.qty.Quantity;
import ch.alpine.tensor.qty.Unit;

class UnicodeUnitTest {
  @Test
  void testOne() {
    String string = UnicodeUnit.of(Unit.ONE);
    assertEquals(string, "");
  }

  @Test
  void testOfUnit() {
    assertEquals(UnicodeUnit.of(Unit.of("kg*m*s^-2")), "kg*m/s\u00b2");
    assertEquals(UnicodeUnit.of(Unit.of("kg*m*s^-2*z^-1")), "kg*m*s\u207b\u00b2*z\u207b\u00b9");
    assertEquals(UnicodeUnit.of(Unit.of("kg*m^2")), "kg*m\u00b2");
    assertEquals(UnicodeUnit.of(Unit.of("m^-2")), "m\u207b\u00b2");
  }

  @Test
  void testOfUnitChar() {
    assertEquals(UnicodeUnit.of(Unit.of("K")), "\u212a");
    assertEquals(UnicodeUnit.of(Unit.of("nOhm")), "n\u2126");
    assertEquals(UnicodeUnit.of(Unit.of("Ohm")), "\u2126");
    assertEquals(UnicodeUnit.of(Unit.of("kOhm")), "k\u2126");
    assertEquals(UnicodeUnit.of(Unit.of("MOhm")), "M\u2126");
    assertEquals(UnicodeUnit.of(Unit.of("GOhm")), "G\u2126");
  }

  @Test
  void testCurrency() {
    assertEquals(UnicodeUnit.of(Unit.of("EUR")), "\u20ac");
    assertEquals(UnicodeUnit.of(Unit.of("USD")), "$");
    assertEquals(UnicodeUnit.of(Unit.of("GBP")), "\u00a3");
    assertEquals(UnicodeUnit.of(Unit.of("JPY")), "\u00a5");
  }

  @Test
  void testIndeterminate() {
    UnicodeScalar.of(Quantity.of(DoubleScalar.INDETERMINATE, "degC"));
  }

  @Test
  void testInfty() {
    UnicodeScalar.of(Quantity.of(DoubleScalar.POSITIVE_INFINITY, "degC"));
    UnicodeScalar.of(Quantity.of(DoubleScalar.NEGATIVE_INFINITY, "degC"));
  }

  @Test
  void testMicro() {
    assertEquals(UnicodeUnit.of(Unit.of("us")), "\u03BCs");
    assertEquals(UnicodeUnit.of(Unit.of("uF")), "\u03BCF");
    assertEquals(UnicodeUnit.of(Unit.of("uOhm")), "\u03BC\u2126");
    assertEquals(UnicodeUnit.of(Unit.of("uS")), "\u03BCS");
  }

  @Test
  void testNullFail() {
    assertThrows(Exception.class, () -> UnicodeUnit.of(null));
  }
}
