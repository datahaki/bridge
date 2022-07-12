// code by jph
package ch.alpine.bridge.ref.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import ch.alpine.bridge.ref.ex.MissingMarkerParam;

class ReflectionMarkersTest {
  @Test
  void test() {
    MissingMarkerParam missingMarkerParam = new MissingMarkerParam();
    int size = ReflectionMarkers.INSTANCE.missing().size();
    ObjectProperties.list(missingMarkerParam);
    assertEquals(size + 6, ReflectionMarkers.INSTANCE.missing().size());
  }

  @ParameterizedTest
  @MethodSource("provideStrings")
  void testArgStream(String string) {
    System.out.println(">" + string);
  }

  private static Stream<Arguments> provideStrings() {
    // AtomicInteger i = new AtomicInteger();
    Stream<List<Integer>> flatMap = IntStream.range(0, 3).boxed().flatMap(i -> {
      return IntStream.range(0, 5).mapToObj(j -> List.of(i, j));
    });
    List<List<Integer>> list = flatMap.collect(Collectors.toList());
    System.out.println(list);
    return Stream.of( //
        Arguments.of(""), //
        Arguments.of("  "), //
        Arguments.of("not blank"));
  }
}
