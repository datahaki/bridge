// code by jph
package ch.alpine.java.ref;

import org.junit.jupiter.api.Test;

import ch.alpine.java.ref.util.PanelFieldsEditor;

class FieldsEditorTest {
  @Test
  public void testSimple() {
    PanelFieldsEditor panelFieldsEditor = new PanelFieldsEditor(new SimpleParam());
    panelFieldsEditor.createJScrollPane();
    panelFieldsEditor.list().forEach(fieldPanel -> fieldPanel.notifyListeners(""));
    panelFieldsEditor.updateJComponents();
  }

  @Test
  public void testV011() {
    PanelFieldsEditor fieldsPanel = new PanelFieldsEditor(new V011Param(3));
    fieldsPanel.createJScrollPane();
  }
}
