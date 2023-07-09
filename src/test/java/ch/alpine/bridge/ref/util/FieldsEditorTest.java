// code by jph
package ch.alpine.bridge.ref.util;

import org.junit.jupiter.api.Test;

import ch.alpine.bridge.ref.ex.SimpleParam;
import ch.alpine.bridge.ref.ex.V011Param;

class FieldsEditorTest {
  @Test
  void testSimple() {
    PanelFieldsEditor panelFieldsEditor = PanelFieldsEditor.splits(new SimpleParam());
    panelFieldsEditor.createJScrollPane();
    panelFieldsEditor.list().forEach(fieldPanel -> fieldPanel.addListener(s -> {
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
