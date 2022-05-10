// code by jph
package ch.alpine.bridge.ref;

import javax.swing.JToolBar;

import org.junit.jupiter.api.Test;

import ch.alpine.bridge.ref.util.FieldsEditor;
import ch.alpine.bridge.ref.util.ToolbarFieldsEditor;

class ToolbarFieldsEditorTest {
  @Test
  public void testSimple() {
    FieldsEditor tfe = ToolbarFieldsEditor.add(new GuiExtension(), new JToolBar());
    tfe.updateJComponents();
  }
}
