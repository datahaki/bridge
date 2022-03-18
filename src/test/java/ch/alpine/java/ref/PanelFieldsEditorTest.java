// code by jph
package ch.alpine.java.ref;

import org.junit.jupiter.api.Test;

import ch.alpine.java.ref.util.PanelFieldsEditor;

public class PanelFieldsEditorTest {
  @Test
  public void testSimple() {
    PanelFieldsEditor panelFieldsEditor = new PanelFieldsEditor(new GuiExtension());
    panelFieldsEditor.addUniversalListener(() -> {
      // ---
    });
    panelFieldsEditor.createJScrollPane();
  }
}
