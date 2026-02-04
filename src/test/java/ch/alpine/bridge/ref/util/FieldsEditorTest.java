// code by jph
package ch.alpine.bridge.ref.util;

import org.junit.jupiter.api.Test;

import demo.SimpleParam;
import demo.V011Param;

class FieldsEditorTest {
  @Test
  void testSimple() {
    PanelFieldsEditor panelFieldsEditor = PanelFieldsEditor.splits(new SimpleParam());
    panelFieldsEditor.createJScrollPane();
    panelFieldsEditor.list().forEach(fieldPanel -> fieldPanel.addListener(_ -> {
      // ---
    }));
    panelFieldsEditor.updateJComponents();
  }

  @Test
  void testV011() {
    PanelFieldsEditor fieldsPanel = PanelFieldsEditor.splits(new V011Param(3));
    fieldsPanel.createJScrollPane();
  }
}
