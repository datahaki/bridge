// code by jph
package ch.alpine.java.ref;

import ch.alpine.java.ref.util.PanelFieldsEditor;
import junit.framework.TestCase;

public class FieldsEditorTest extends TestCase {
  public void testSimple() {
    PanelFieldsEditor panelFieldsEditor = new PanelFieldsEditor(new SimpleParam());
    panelFieldsEditor.createJScrollPane();
    panelFieldsEditor.list().forEach(fieldPanel -> fieldPanel.notifyListeners(""));
    panelFieldsEditor.updateJComponents();
  }

  public void testV011() {
    PanelFieldsEditor fieldsPanel = new PanelFieldsEditor(new V011Param(3));
    fieldsPanel.createJScrollPane();
  }
}
