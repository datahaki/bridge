// code by jph
package ch.alpine.bridge.ref.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class InvalidFieldDetectionTest {
  @Test
  void testFieldClip() {
    assertFalse(InvalidFieldDetection.isEmpty(new ExampleBadFieldClip()));
  }

  @Test
  void testClip() {
    assertThrows(Exception.class, () -> InvalidFieldDetection.of(new ExampleBadClip()));
  }

  @Test
  void testFieldInteger() {
    assertFalse(InvalidFieldDetection.isEmpty(new ExampleBadFieldIntegerValue()));
  }

  @Test
  void testBadMethod() {
    assertFalse(InvalidFieldDetection.isEmpty(new ExampleBadMethod()));
  }

  @Test
  void testBadReturn() {
    assertFalse(InvalidFieldDetection.isEmpty(new ExampleBadReturn()));
  }

  @Test
  void testBadFuse() {
    assertFalse(InvalidFieldDetection.isEmpty(new ExampleBadFuse()));
  }

  @Test
  void testFileAndDirectory() {
    assertFalse(InvalidFieldDetection.isEmpty(new ExampleBadFile()));
    assertFalse(InvalidFieldDetection.isEmpty(new ExampleBadDirectory()));
  }

  @Test
  void testNull() {
    assertFalse(InvalidFieldDetection.isEmpty(new ExampleNullValue()));
  }
}
