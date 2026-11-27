// code by jph
package ch.alpine.bridge.math;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ch.alpine.tensor.Rational;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Throw;
import ch.alpine.tensor.ext.Serialization;
import ch.alpine.tensor.io.StringScalar;
import ch.alpine.tensor.num.Pi;
import ch.alpine.tensor.qty.Quantity;
import ch.alpine.tensor.qty.QuantityMagnitude;
import ch.alpine.tensor.qty.Unit;
import ch.alpine.tensor.qty.UnitSystem;

class UnitHubTest {
  @Test
  void test() {
    assertThrows(Exception.class, () -> UnitHub.si((Unit) null));
  }

  @Test
  void testSerializable() {
    UnitHub unitHub = UnitHub.si("s*m^-1");
    Serialization.copy(unitHub);
    // assertThrows(Exception.class, () -> Serialization.copy(unitHub));
    assertEquals(unitHub.zero(), Quantity.of(0, "m^-1*s"));
    assertTrue(unitHub.toString().startsWith("UnitHub["));
    unitHub.quantityMagnitude(Quantity.of(3, "h*km^-1"));
  }

  @Test
  void testFullRotation() {
    assertEquals(SI.DEGREES.equipWithUnitNormalize(360), Pi.TWO);
  }

  @Test
  void testReciprocal() {
    Scalar rad = RealScalar.of(0.2617993877991494);
    Scalar scalar = SI.DEGREES.equipWithUnitNormalize(15).reciprocal();
    assertEquals(scalar.multiply(rad), RealScalar.ONE);
  }

  @Test
  void testReciprocal10() {
    Scalar rad = RealScalar.of(0.17453292519943295);
    Scalar scalar = SI.DEGREES.equipWithUnitNormalize(10);
    scalar = scalar.reciprocal();
    assertEquals(scalar.multiply(rad), RealScalar.ONE);
  }

  @Test
  void testTurns() {
    Unit turns = Unit.of("turns");
    assertEquals(UnitSystem.SI().apply(Quantity.of(Rational.HALF, turns)), Pi.VALUE);
    assertEquals(QuantityMagnitude.SI().in(Unit.ONE).apply(Quantity.of(1, turns)), Pi.TWO);
  }

  @Test
  void testStringScalarFail() {
    assertThrows(Throw.class, () -> SI.DEGREES.equipWithUnitNormalize(StringScalar.of("abc")));
  }

  @Test
  void testBytes() {
    assertEquals(QuantityMagnitude.SI().in("B").apply(Quantity.of(3, "MB")), RealScalar.of(3000_000));
    assertEquals(QuantityMagnitude.SI().in("kB").apply(Quantity.of(30_000, "B")), RealScalar.of(30));
    assertEquals(QuantityMagnitude.SI().in("TB").apply(Quantity.of(2, "PB")), RealScalar.of(2000));
  }

  @Test
  void testSome() {
    Scalar one = Quantity.of(1000, "‰");
    assertEquals(RealScalar.ONE, UnitSystem.SI().apply(one));
  }
}
