// code by jph
package ch.alpine.java.ref;

import junit.framework.TestCase;

public class PanelFieldsEditorTest extends TestCase {
  public void testSimple() {
    PanelFieldsEditor panelFieldsEditor = new PanelFieldsEditor(new GuiExtension());
    panelFieldsEditor.addUniversalListener(() -> {
      // ---
    });
    panelFieldsEditor.createJScrollPane();
  }
}
