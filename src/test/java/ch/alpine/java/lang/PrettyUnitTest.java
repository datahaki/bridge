// code by jph
package ch.alpine.java.lang;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import ch.alpine.java.util.AssertFail;
import ch.alpine.tensor.DoubleScalar;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.qty.Quantity;
import ch.alpine.tensor.qty.Unit;

public class PrettyUnitTest {
  @Test
  public void testOne() {
    String string = PrettyUnit.of(Unit.ONE);
    assertEquals(string, "");
  }

  @Test
  public void testOfUnit() {
    assertEquals(PrettyUnit.of(Unit.of("kg*m*s^-2")), "kg*m/s\u00b2");
    assertEquals(PrettyUnit.of(Unit.of("kg*m*s^-2*z^-1")), "kg*m*s\u207b\u00b2*z\u207b\u00b9");
    assertEquals(PrettyUnit.of(Unit.of("kg*m^2")), "kg*m\u00b2");
    assertEquals(PrettyUnit.of(Unit.of("m^-2")), "m\u207b\u00b2");
  }

  @Test
  public void testOfUnitChar() {
    assertEquals(PrettyUnit.of(Unit.of("EUR")), "\u20ac");
    assertEquals(PrettyUnit.of(Unit.of("K")), "\u212a");
    assertEquals(PrettyUnit.of(Unit.of("Ohm")), "\u2126");
    assertEquals(PrettyUnit.of(Unit.of("kOhm")), "k\u2126");
    assertEquals(PrettyUnit.of(Unit.of("MOhm")), "M\u2126");
  }

  @Test
  public void testQuantity() {
    assertEquals(PrettyUnit.of(RealScalar.of(3)), "3");
    assertEquals(PrettyUnit.of(Quantity.of(3, "s^2")), "3 s\u00b2");
    assertEquals(PrettyUnit.of(Quantity.of(3, "s^-2")), "3 s\u207b\u00b2");
    assertEquals(PrettyUnit.of(Quantity.of(3, "m*s^-2")), "3 m/s\u00b2");
    assertEquals(PrettyUnit.of(Quantity.of(4, "s^3")), "4 s\u00b3");
    assertEquals(PrettyUnit.of(Quantity.of(4, "s^-3")), "4 s\u207b\u00b3");
    assertEquals(PrettyUnit.of(Quantity.of(5, "s^4")), "5 s^4");
    assertEquals(PrettyUnit.of(Quantity.of(5, "s^-4")), "5 s^-4");
  }

  @Test
  public void testDegC() {
    assertEquals(PrettyUnit.of(Quantity.of(-23, "degC")), "-23 \u2103");
  }

  @Test
  public void testIndeterminate() {
    PrettyUnit.of(Quantity.of(DoubleScalar.INDETERMINATE, "degC"));
  }

  @Test
  public void testMicro() {
    assertEquals(PrettyUnit.of(Unit.of("us")), "\u03BCs");
    assertEquals(PrettyUnit.of(Unit.of("uF")), "\u03BCF");
  }

  @Test
  public void testNullFail() {
    AssertFail.of(() -> PrettyUnit.of((Unit) null));
  }
}
