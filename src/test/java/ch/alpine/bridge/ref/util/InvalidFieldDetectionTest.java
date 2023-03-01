// code by jph
package ch.alpine.bridge.ref.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import ch.alpine.bridge.ref.ex.ClipParam;
import ch.alpine.bridge.ref.ex.ExampleBadClip;
import ch.alpine.bridge.ref.ex.ExampleBadDirectory;
import ch.alpine.bridge.ref.ex.ExampleBadFieldClip;
import ch.alpine.bridge.ref.ex.ExampleBadFile;
import ch.alpine.bridge.ref.ex.ExampleBadFuse;
import ch.alpine.bridge.ref.ex.ExampleBadMethod;
import ch.alpine.bridge.ref.ex.ExampleBadReturn;
import ch.alpine.bridge.ref.ex.ExampleBadScalar;
import ch.alpine.bridge.ref.ex.ExampleNullValue;
import ch.alpine.bridge.ref.ex.GuiExtension;

class InvalidFieldDetectionTest {
  @Test
  void testClipParam() {
    assertTrue(InvalidFieldDetection.isEmpty(new ClipParam()));
  }

  @Test
  void testGuiExtension() {
    // TODO BRIDGE DOC document specifially in class where invalid field assignments are intended
    List<FieldValueContainer> list = InvalidFieldDetection.of(new GuiExtension());
    list.forEach(System.out::println);
    assertEquals(list.size(), 2);
  }

  @Test
  void testFieldClip() {
    assertFalse(InvalidFieldDetection.isEmpty(new ExampleBadFieldClip()));
  }

  @Test
  void testStringScalar() {
    assertFalse(InvalidFieldDetection.isEmpty(new ExampleBadScalar()));
  }

  @Test
  void testClip() {
    assertThrows(Exception.class, () -> InvalidFieldDetection.of(new ExampleBadClip()));
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
    assertTrue(InvalidFieldDetection.isEmpty(new ExampleNullValue()));
  }
}
