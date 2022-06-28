// code by jph
package ch.alpine.bridge.ref;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import ch.alpine.bridge.ref.util.ObjectProperties;
import ch.alpine.bridge.ref.util.ReflectionMarkers;

class ReflectionMarkersTest {
  @Test
  void test() {
    MissingMarkerParam missingMarkerParam = new MissingMarkerParam();
    int size = ReflectionMarkers.INSTANCE.missing().size();
    ObjectProperties.list(missingMarkerParam);
    assertEquals(size + 6, ReflectionMarkers.INSTANCE.missing().size());
  }
}
