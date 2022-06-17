// code by jph
package ch.alpine.bridge.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ch.alpine.tensor.img.ColorDataGradients;

class EnumMultimapTest {
  @Test
  void test() {
    EnumMultimap<ColorDataGradients, String> enumMultimap = new EnumMultimap<>(ColorDataGradients.class);
    assertTrue(enumMultimap.isEmpty());
    enumMultimap.put(ColorDataGradients.ALPINE, "here");
    assertEquals(enumMultimap.stream().count(), 1);
    assertThrows(Exception.class, () -> enumMultimap.put(ColorDataGradients.ALPINE, "here"));
    assertEquals(enumMultimap.values().size(), ColorDataGradients.values().length);
  }
}
