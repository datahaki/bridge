// code by jph
package ch.alpine.java.ref;

import javax.swing.JToolBar;

import junit.framework.TestCase;

public class ToolbarFieldsEditorTest extends TestCase {
  public void testSimple() {
    ToolbarFieldsEditor tfe = ToolbarFieldsEditor.add(new GuiExtension(), new JToolBar());
    tfe.updateJComponents();
  }
}