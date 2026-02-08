// code by jph
package ch.alpine.bridge.lang;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.reflect.Modifier;
import java.math.BigInteger;

import org.junit.jupiter.api.Test;

import ch.alpine.tensor.DoubleScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.qty.Quantity;
import ch.alpine.tensor.qty.Unit;

class UnicodeUnitTest {
  private static final String T = "\u2009";
  private static final char D = '\u2215';

  @Test
  void testOne() {
    String string = UnicodeString.of(Unit.ONE);
    assertEquals(string, "");
  }

  @Test
  void testOfUnit() {
    assertEquals(UnicodeString.of(Unit.of("kg*m*s^-2")), "kg" + T + "m" + D + "s\u00b2");
    assertEquals(UnicodeString.of(Unit.of("kg*m*s^-2*z^-1")), "kg" + T + "m" + T + "s\u207b\u00b2" + T + "z\u207b\u00b9");
    assertEquals(UnicodeString.of(Unit.of("kg*m^2")), "kg" + T + "m\u00b2");
    assertEquals(UnicodeString.of(Unit.of("m^-2")), "m\u207b\u00b2");
  }

  @Test
  void testOfUnitChar() {
    assertEquals(UnicodeString.of(Unit.of("K")), "\u212a");
    assertEquals(UnicodeString.of(Unit.of("nOhm")), "n\u2126");
    assertEquals(UnicodeString.of(Unit.of("Ohm")), "\u2126");
    assertEquals(UnicodeString.of(Unit.of("kOhm")), "k\u2126");
    assertEquals(UnicodeString.of(Unit.of("MOhm")), "M\u2126");
    assertEquals(UnicodeString.of(Unit.of("GOhm")), "G\u2126");
  }

  @Test
  void testCurrency() {
    assertEquals(UnicodeString.of(Unit.of("EUR")), "\u20ac");
    assertEquals(UnicodeString.of(Unit.of("USD")), "$");
    assertEquals(UnicodeString.of(Unit.of("GBP")), "\u00a3");
    assertEquals(UnicodeString.of(Unit.of("JPY")), "\u00a5");
  }

  @Test
  void testIndeterminate() {
    UnicodeString.of(Quantity.of(DoubleScalar.INDETERMINATE, "degC"));
  }

  @Test
  void testInfty() {
    UnicodeString.of(Quantity.of(DoubleScalar.POSITIVE_INFINITY, "degC"));
    UnicodeString.of(Quantity.of(DoubleScalar.NEGATIVE_INFINITY, "degC"));
  }

  @Test
  void testMicro() {
    assertEquals(UnicodeString.of(Unit.of("us")), "\u03BCs");
    assertEquals(UnicodeString.of(Unit.of("uF")), "\u03BCF");
    assertEquals(UnicodeString.of(Unit.of("uOhm")), "\u03BC\u2126");
    assertEquals(UnicodeString.of(Unit.of("uS")), "\u03BCS");
  }

  @Test
  void testNullFail() {
    assertThrows(Exception.class, () -> UnicodeString.of((Scalar) null));
    assertThrows(Exception.class, () -> UnicodeString.of((BigInteger) null));
    assertThrows(Exception.class, () -> UnicodeString.of((Unit) null));
  }

  @Test
  void testPackageVisibility() {
    assertFalse(Modifier.isPublic(UnicodeUnit.class.getModifiers()));
  }
}
