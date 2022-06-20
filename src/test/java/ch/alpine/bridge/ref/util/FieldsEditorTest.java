// code by jph
package ch.alpine.bridge.ref.util;

import org.junit.jupiter.api.Test;

import ch.alpine.bridge.ref.SimpleParam;
import ch.alpine.bridge.ref.V011Param;

class FieldsEditorTest {
  @Test
  void testSimple() {
    PanelFieldsEditor panelFieldsEditor = new PanelFieldsEditor(new SimpleParam());
    panelFieldsEditor.createJScrollPane();
    panelFieldsEditor.list().forEach(fieldPanel -> fieldPanel.addListener(s -> {
      // ---
    }));
    panelFieldsEditor.updateJComponents();
  }

  @Test
  void testV011() {
    PanelFieldsEditor fieldsPanel = new PanelFieldsEditor(new V011Param(3));
    fieldsPanel.createJScrollPane();
  }
}
