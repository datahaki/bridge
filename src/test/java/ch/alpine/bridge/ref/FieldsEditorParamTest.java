package ch.alpine.bridge.ref;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import ch.alpine.bridge.ref.util.ObjectProperties;
import ch.alpine.bridge.ref.util.RandomFieldsAssignment;

class FieldsEditorParamTest {
  private static final int NUMEL = 10;
  private static final Set<String> SET = new HashSet<>();

  static Stream<Object> stream() {
    FieldsEditorParam fieldsEditorParam = new FieldsEditorParam();
    return RandomFieldsAssignment.of(fieldsEditorParam).randomize(NUMEL);
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
}
