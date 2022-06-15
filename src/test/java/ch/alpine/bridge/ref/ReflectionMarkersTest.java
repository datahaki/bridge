// code by jph
package ch.alpine.bridge.ref;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ReflectionMarkersTest {
  @Test
  void test() {
    MissingMarkerParam missingMarkerParam = new MissingMarkerParam();
    int size = ReflectionMarkers.INSTANCE.missing().size();
    ObjectProperties.list(missingMarkerParam);
    assertEquals(size + 6, ReflectionMarkers.INSTANCE.missing().size());
  }
}
