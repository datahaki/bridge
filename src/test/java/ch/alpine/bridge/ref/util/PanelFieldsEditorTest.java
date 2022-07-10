// code by jph
package ch.alpine.bridge.ref.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JComponent;
import javax.swing.JPanel;

import org.junit.jupiter.api.Test;

import ch.alpine.bridge.ref.FieldPanel;
import ch.alpine.bridge.ref.ex.GuiExtension;
import ch.alpine.bridge.ref.ex.OtherPackageParam;

class PanelFieldsEditorTest {
  @Test
  void testSimple() {
    PanelFieldsEditor panelFieldsEditor = new PanelFieldsEditor(new GuiExtension());
    panelFieldsEditor.addUniversalListener(() -> {
      // ---
    });
    panelFieldsEditor.createJScrollPane();
  }

  @Test
  void testEmptyPanel() {
    OtherPackageParam otherPackageParam = new OtherPackageParam();
    PanelFieldsEditor panelFieldsEditor = new PanelFieldsEditor(otherPackageParam);
    assertTrue(panelFieldsEditor.list().isEmpty());
    JPanel jPanel = panelFieldsEditor.getJPanel();
    assertEquals(jPanel.getComponentCount(), 0);
  }

  @Test
  void testInstances() {
    PanelFieldsEditor panelFieldsEditor = new PanelFieldsEditor(new GuiExtension());
    List<JComponent> l1 = panelFieldsEditor.list().stream().map(FieldPanel::getJComponent).collect(Collectors.toList());
    List<JComponent> l2 = panelFieldsEditor.list().stream().map(FieldPanel::getJComponent).collect(Collectors.toList());
    assertEquals(l1, l2);
  }

  @Test
  void testNullValues() {
    GuiExtension guiExtension = new GuiExtension();
    guiExtension.cdg = null;
    guiExtension.pivots = null;
    PanelFieldsEditor panelFieldsEditor = new PanelFieldsEditor(guiExtension);
    panelFieldsEditor.createJScrollPane();
  }
}
