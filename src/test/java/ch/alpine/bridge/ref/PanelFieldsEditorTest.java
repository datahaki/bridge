// code by jph
package ch.alpine.bridge.ref;

import org.junit.jupiter.api.Test;

import ch.alpine.bridge.ref.util.PanelFieldsEditor;

class PanelFieldsEditorTest {
  @Test
  public void testSimple() {
    PanelFieldsEditor panelFieldsEditor = new PanelFieldsEditor(new GuiExtension());
    panelFieldsEditor.addUniversalListener(() -> {
      // ---
    });
    panelFieldsEditor.createJScrollPane();
  }
}
