// code by jph
package ch.alpine.java.ref.gui;

import junit.framework.TestCase;

public class GuiExtensionTest extends TestCase {
  public void testSimple() {
    GuiExtension guiExtension = new GuiExtension();
    FieldsPanel fieldsEditor = new FieldsPanel(guiExtension);
    fieldsEditor.addUniversalListener(() -> System.out.println("changed"));
    fieldsEditor.getJScrollPane();
  }
}
