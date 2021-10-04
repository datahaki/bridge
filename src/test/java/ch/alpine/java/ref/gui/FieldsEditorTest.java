// code by jph
package ch.alpine.java.ref.gui;

import ch.alpine.java.ref.SimpleParam;
import ch.alpine.java.ref.V011Param;
import junit.framework.TestCase;

public class FieldsEditorTest extends TestCase {
  public void testSimple() {
    FieldsPanel fieldsEditor = new FieldsPanel(new SimpleParam());
    fieldsEditor.getJScrollPane();
    fieldsEditor.list().forEach(fieldPanel -> fieldPanel.notifyListeners(""));
  }

  public void testV011() {
    FieldsPanel fieldsEditor = new FieldsPanel(new V011Param(3));
    fieldsEditor.getJScrollPane();
  }
}
