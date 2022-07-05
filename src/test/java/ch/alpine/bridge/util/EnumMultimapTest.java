// code by jph
package ch.alpine.bridge.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import ch.alpine.tensor.img.ColorDataGradients;

class EnumMultimapTest {
  @Test
  void test() {
    EnumMultimap<ColorDataGradients, String> enumMultimap = EnumMultimap.withCopyOnWriteLinkedSet(ColorDataGradients.class);
    assertTrue(enumMultimap.isEmpty());
    enumMultimap.put(ColorDataGradients.ALPINE, "abc");
    enumMultimap.put(ColorDataGradients.ALPINE, "def");
    assertThrows(Exception.class, () -> enumMultimap.put(ColorDataGradients.ALPINE, "abc"));
    assertEquals(enumMultimap.valuesCount(), 2);
    assertEquals(enumMultimap.stream().count(), 2);
    enumMultimap.put(ColorDataGradients.DEEP_SEA, "abc1");
    assertThrows(Exception.class, () -> enumMultimap.remove(ColorDataGradients.DEEP_SEA, "abc2"));
    List<String> list = enumMultimap.values_stream().collect(Collectors.toList());
    assertEquals(list.toString(), "[abc1, abc, def]");
    enumMultimap.remove(ColorDataGradients.DEEP_SEA, "abc1");
    assertFalse(enumMultimap.isEmpty());
  }
}
