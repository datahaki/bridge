// code by jph
package ch.alpine.java.ref;

import javax.swing.JToolBar;

import org.junit.jupiter.api.Test;

import ch.alpine.java.ref.util.ToolbarFieldsEditor;

public class ToolbarFieldsEditorTest {
  @Test
  public void testSimple() {
    ToolbarFieldsEditor tfe = ToolbarFieldsEditor.add(new GuiExtension(), new JToolBar());
    tfe.updateJComponents();
  }
}
