// code by jph
package ch.alpine.bridge.lang;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import ch.alpine.tensor.ext.Serialization;
import ch.alpine.tensor.qty.Quantity;
import ch.alpine.tensor.qty.Unit;

class UnitHubTest {
  @Test
  void test() {
    assertThrows(Exception.class, () -> UnitHub.si((Unit) null));
  }

  @Test
  void testSerializable() {
    UnitHub unitHub = UnitHub.si("s*m^-1");
    assertThrows(Exception.class, () -> Serialization.copy(unitHub));
    assertEquals(unitHub.zero(), Quantity.of(0, "m^-1*s"));
  }
}
