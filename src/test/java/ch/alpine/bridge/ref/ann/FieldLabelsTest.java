// code by jph
package ch.alpine.bridge.ref.ann;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import ch.alpine.bridge.ref.ex.FieldLabelsT;
import ch.alpine.bridge.ref.util.PanelFieldsEditor;

class FieldLabelsTest {
  @Test
  void testFormatFail() {
    FieldLabelsT fieldLabelsT = new FieldLabelsT();
    PanelFieldsEditor fieldsPanel = PanelFieldsEditor.splits(fieldLabelsT);
    assertEquals(fieldsPanel.list().size(), 4);
    fieldsPanel.createJScrollPane();
  }
}
