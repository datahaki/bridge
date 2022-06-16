// code by jph
package ch.alpine.bridge.ref;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class FieldsEditorKeyTest {
  @Test
  void test() {
    String key = FieldsEditorKey.FONT_TEXTFIELD.key();
    assertEquals(key, "FieldsEditorKey.FONT_TEXTFIELD");
  }
}
