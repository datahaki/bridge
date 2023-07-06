// code by jph
package ch.alpine.bridge.lang;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

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
  void testSerializable() throws ClassNotFoundException, IOException {
    UnitHub unitHub = UnitHub.si("s*m^-1");
    Serialization.copy(unitHub);
    // assertThrows(Exception.class, () -> Serialization.copy(unitHub));
    assertEquals(unitHub.zero(), Quantity.of(0, "m^-1*s"));
    assertTrue(unitHub.toString().startsWith("UnitHub["));
    unitHub.magnitude(Quantity.of(3, "h*km^-1"));
  }
}
