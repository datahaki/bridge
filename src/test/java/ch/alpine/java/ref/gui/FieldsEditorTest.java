// code by jph
package ch.alpine.java.ref.gui;

import ch.alpine.java.ref.SimpleParam;
import ch.alpine.java.ref.V011Param;
import junit.framework.TestCase;

public class FieldsEditorTest extends TestCase {
  public void testSimple() {
    PanelFieldsEditor fieldsPanel = new PanelFieldsEditor(new SimpleParam());
    fieldsPanel.createJScrollPane();
    fieldsPanel.list().forEach(fieldPanel -> fieldPanel.notifyListeners(""));
  }

  public void testV011() {
    PanelFieldsEditor fieldsPanel = new PanelFieldsEditor(new V011Param(3));
    fieldsPanel.createJScrollPane();
  }
}
