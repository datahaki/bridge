// code by jph
package ch.alpine.bridge.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Map.Entry;

import org.junit.jupiter.api.Test;

class ImmutableEntryTest {
  @Test
  void test() {
    Entry<Integer, String> entry = ImmutableEntry.of(3, "abc");
    assertEquals(entry.getKey(), 3);
    assertEquals(entry.getValue(), "abc");
    assertThrows(Exception.class, () -> entry.setValue("123"));
  }
}
