// code by jph
package ch.alpine.bridge.ref.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import test.data.MissingMarkerParam;

class ReflectionMarkersTest {
  @Test
  void test() {
    MissingMarkerParam missingMarkerParam = new MissingMarkerParam();
    int size = ReflectionMarkers.INSTANCE.missing().size();
    ObjectProperties.list(missingMarkerParam);
    assertEquals(size + 6, ReflectionMarkers.INSTANCE.missing().size());
  }

  private static Stream<Arguments> provideStrings() {
    return Stream.of( //
        Arguments.of(""), //
        Arguments.of("  "), //
        Arguments.of("     "));
  }

  @ParameterizedTest
  @MethodSource("provideStrings")
  void testArgStream(String string) {
    assertTrue(string.trim().isEmpty());
  }
}
