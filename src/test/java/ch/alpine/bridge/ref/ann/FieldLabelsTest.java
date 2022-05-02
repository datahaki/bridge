// code by jph
package ch.alpine.bridge.ref.ann;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import ch.alpine.bridge.ref.util.PanelFieldsEditor;

class FieldLabelsTest {
  @Test
  public void testFormatFail() {
    FieldLabelsT fieldLabelsT = new FieldLabelsT();
    PanelFieldsEditor fieldsPanel = new PanelFieldsEditor(fieldLabelsT);
    assertEquals(fieldsPanel.list().size(), 4);
    fieldsPanel.createJScrollPane();
  }
}
