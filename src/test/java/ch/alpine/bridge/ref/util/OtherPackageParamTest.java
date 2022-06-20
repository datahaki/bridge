// code by jph
package ch.alpine.bridge.ref.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.swing.JPanel;
import javax.swing.JToolBar;

import org.junit.jupiter.api.Test;

class OtherPackageParamTest {
  @Test
  void testEmptyPanel() {
    OtherPackageParam otherPackageParam = new OtherPackageParam();
    PanelFieldsEditor panelFieldsEditor = new PanelFieldsEditor(otherPackageParam);
    assertTrue(panelFieldsEditor.list().isEmpty());
    JPanel jPanel = panelFieldsEditor.getJPanel();
    assertEquals(jPanel.getComponentCount(), 0);
  }

  @Test
  void testEmptyToolbar() {
    OtherPackageParam otherPackageParam = new OtherPackageParam();
    JToolBar jToolBar = new JToolBar();
    FieldsEditor fieldsEditor = ToolbarFieldsEditor.add(otherPackageParam, jToolBar);
    assertTrue(fieldsEditor.list().isEmpty());
    assertEquals(jToolBar.getComponentCount(), 0);
  }
}
