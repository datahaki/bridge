// code by jph
package ch.alpine.bridge.ref.ann;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import ch.alpine.bridge.ref.ex.FieldClipT;
import ch.alpine.bridge.ref.util.PanelFieldsEditor;

@ReflectionMarker
class FieldClipTest {
  @Test
  void testFailEx() {
    FieldClipT fieldClipCorrupt = new FieldClipT();
    assertThrows(Exception.class, () -> PanelFieldsEditor.splits(fieldClipCorrupt));
  }
}
