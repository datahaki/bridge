// code by jph
package sys.dat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class UniqueValueTest {
  @Test
  void testSimple() {
    UniqueValue<Integer> uniqueValue = UniqueValue.empty();
    assertFalse(uniqueValue.isPresent());
    assertThrows(Exception.class, () -> uniqueValue.orElseThrow());
    assertEquals(uniqueValue.orElse(4), 4);
    uniqueValue.set(3);
    assertEquals(uniqueValue.orElse(4), 3);
    assertTrue(uniqueValue.isPresent());
    assertEquals(uniqueValue.orElseThrow(), 3);
    uniqueValue.set(3);
    uniqueValue.set(3);
    assertThrows(Exception.class, () -> uniqueValue.set(5));
    assertEquals(uniqueValue.orElseThrow(), 3);
  }

  @Test
  void testInit() {
    UniqueValue<Integer> uniqueValue = UniqueValue.of(3);
    assertTrue(uniqueValue.isPresent());
    assertEquals(uniqueValue.orElse(4), 3);
    uniqueValue.set(3);
    assertTrue(uniqueValue.isPresent());
    assertEquals(uniqueValue.orElseThrow(), 3);
    uniqueValue.set(3);
    uniqueValue.set(3);
    assertThrows(Exception.class, () -> uniqueValue.set(5));
    assertEquals(uniqueValue.orElseThrow(), 3);
  }
}
