// code by jph
package ch.alpine.bridge.ref;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JComponent;

import org.junit.jupiter.api.Test;

import ch.alpine.bridge.ref.util.PanelFieldsEditor;

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
