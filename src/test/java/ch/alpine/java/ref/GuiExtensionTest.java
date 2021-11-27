// code by jph
package ch.alpine.java.ref;

import junit.framework.TestCase;

public class GuiExtensionTest extends TestCase {
  public void testSimple() {
    GuiExtension guiExtension = new GuiExtension();
    PanelFieldsEditor fieldsPanel = new PanelFieldsEditor(guiExtension);
    fieldsPanel.addUniversalListener(() -> System.out.println("changed"));
    fieldsPanel.createJScrollPane();
  }
}
