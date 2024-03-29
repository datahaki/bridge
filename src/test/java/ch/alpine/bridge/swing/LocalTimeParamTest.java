// code by jph
package ch.alpine.bridge.swing;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalTime;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import ch.alpine.bridge.ref.util.FieldsAssignment;
import ch.alpine.bridge.ref.util.ObjectProperties;

class LocalTimeParamTest {
  private static final int NUMEL = 10;
  private static final Set<String> SET = new HashSet<>();

  static Stream<Object> stream() {
    return FieldsAssignment.of(new LocalTimeParam(LocalTime.NOON)).randomize(new Random(3), NUMEL);
  }

  @ParameterizedTest
  @MethodSource("stream")
  void testHere(Object object) {
    SET.add(ObjectProperties.join(object));
  }

  @AfterAll
  static void afterAll() {
    assertEquals(SET.size(), NUMEL);
  }

  @Test
  void testSimple() {
    LocalTime localTime = LocalTime.now();
    LocalTimeParam localTimeParam = new LocalTimeParam(localTime);
    LocalTime comp = localTimeParam.toLocalTime();
    assertEquals(localTime, comp);
  }
}
