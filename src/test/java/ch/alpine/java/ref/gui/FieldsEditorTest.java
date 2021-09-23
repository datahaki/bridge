// code by jph
package ch.alpine.java.ref.gui;

import ch.alpine.java.ref.SimpleParam;
import ch.alpine.java.ref.V011Param;
import junit.framework.TestCase;

public class FieldsEditorTest extends TestCase {
  public void testSimple() {
    FieldsEditor fieldsEditor = new FieldsEditor(new SimpleParam());
    fieldsEditor.getFieldsAndTextarea();
    fieldsEditor.list().forEach(fieldPanel -> fieldPanel.notifyListeners(""));
  }

  public void testV011() {
    FieldsEditor fieldsEditor = new FieldsEditor(new V011Param(3));
    fieldsEditor.getFieldsAndTextarea();
  }
}
