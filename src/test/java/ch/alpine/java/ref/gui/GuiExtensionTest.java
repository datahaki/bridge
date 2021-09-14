// code by jph
package ch.alpine.java.ref.gui;

import junit.framework.TestCase;

public class GuiExtensionTest extends TestCase {
  public void testSimple() {
    GuiExtension guiExtension = new GuiExtension();
    FieldsEditor fieldsEditor = new FieldsEditor(guiExtension);
    fieldsEditor.addUniversalListener(() -> System.out.println("changed"));
    fieldsEditor.getFieldsAndTextarea();
  }
}
