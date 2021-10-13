// code by jph
package ch.alpine.java.lang;

import ch.alpine.tensor.DoubleScalar;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.qty.Quantity;
import ch.alpine.tensor.qty.Unit;
import junit.framework.TestCase;

public class PrettyUnitTest extends TestCase {
  public void test() {
    assertEquals(PrettyUnit.of(Unit.of("kg*m*s^-2")), "kg*m/s\u00b2");
    assertEquals(PrettyUnit.of(Unit.of("kg*m*s^-2*z^-1")), "kg*m*s\u207b\u00b2*z\u207b\u00b9");
    assertEquals(PrettyUnit.of(Unit.of("kg*m^2")), "kg*m\u00b2");
    assertEquals(PrettyUnit.of(Unit.of("m^-2")), "m\u207b\u00b2");
  }

  public void testQuantity() {
    assertEquals(PrettyUnit.of(RealScalar.of(3)), "3");
    assertEquals(PrettyUnit.of(Quantity.of(3, "s^2")), "3 s\u00b2");
    assertEquals(PrettyUnit.of(Quantity.of(3, "s^-2")), "3 s\u207b\u00b2");
    assertEquals(PrettyUnit.of(Quantity.of(3, "m*s^-2")), "3 m/s\u00b2");
    assertEquals(PrettyUnit.of(Quantity.of(4, "s^3")), "4 s\u00b3");
    assertEquals(PrettyUnit.of(Quantity.of(4, "s^-3")), "4 s\u207b\u00b3");
  }

  public void testDegC() {
    assertEquals(PrettyUnit.of(Quantity.of(-23, "degC")), "-23 \u2103");
  }

  public void testIndeterminate() {
    PrettyUnit.of(Quantity.of(DoubleScalar.INDETERMINATE, "degC"));
  }

  public void testMicro() {
    assertEquals(PrettyUnit.of(Unit.of("us")), "\u03BCs");
    assertEquals(PrettyUnit.of(Unit.of("uF")), "\u03BCF");
  }
}
