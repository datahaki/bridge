// code by jph
package ch.alpine.bridge.ref.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JComponent;

import org.junit.jupiter.api.Test;

import ch.alpine.bridge.ref.FieldPanel;
import ch.alpine.bridge.ref.FieldWrap;
import ch.alpine.bridge.ref.ex.FieLabParam;
import ch.alpine.bridge.ref.ex.GuiExtension;
import ch.alpine.bridge.ref.ex.OtherPackageParam;
import ch.alpine.bridge.ref.ex.SliderFailParam;

class PanelFieldsEditorTest {
  @Test
  void testSimple() {
    PanelFieldsEditor panelFieldsEditor = PanelFieldsEditor.splits(new GuiExtension());
    panelFieldsEditor.addUniversalListener(() -> {
      // ---
    });
    panelFieldsEditor.createJScrollPane();
  }

  @Test
  void testEmptyPanel() {
    OtherPackageParam otherPackageParam = new OtherPackageParam();
    PanelFieldsEditor panelFieldsEditor = PanelFieldsEditor.splits(otherPackageParam);
    assertTrue(panelFieldsEditor.list().isEmpty());
    JComponent jPanel = panelFieldsEditor.getJPanel();
    assertEquals(jPanel.getComponentCount(), 0);
  }

  @Test
  void testInstances() {
    PanelFieldsEditor panelFieldsEditor = PanelFieldsEditor.splits(new GuiExtension());
    List<JComponent> l1 = panelFieldsEditor.list().stream().map(FieldPanel::getJComponent).collect(Collectors.toList());
    List<JComponent> l2 = panelFieldsEditor.list().stream().map(FieldPanel::getJComponent).collect(Collectors.toList());
    assertEquals(l1, l2);
  }

  @Test
  void testNullValues() {
    GuiExtension guiExtension = new GuiExtension();
    guiExtension.cdg = null;
    guiExtension.pivots = null;
    PanelFieldsEditor panelFieldsEditor = PanelFieldsEditor.splits(guiExtension);
    panelFieldsEditor.createJScrollPane();
  }

  @Test
  void testFieLabParam() {
    ObjectProperties.join(new FieLabParam(4));
    PanelFieldsEditor fieldsPanel = PanelFieldsEditor.splits(new FieLabParam(4));
    fieldsPanel.createJScrollPane();
  }

  @Test
  void testGuiExtension() {
    GuiExtension guiExtension = new GuiExtension();
    PanelFieldsEditor fieldsPanel = PanelFieldsEditor.splits(guiExtension);
    fieldsPanel.addUniversalListener(() -> System.out.println("changed"));
    fieldsPanel.createJScrollPane();
    List<FieldPanel> list = fieldsPanel.list();
    for (FieldPanel fieldPanel : list) {
      assertThrows(Exception.class, () -> fieldPanel.updateJComponent(null));
      assertThrows(Exception.class, () -> fieldPanel.addListener(null));
    }
    for (FieldPanel fieldPanel : list) {
      FieldWrap fieldWrap = fieldPanel.fieldWrap();
      assertThrows(Exception.class, () -> fieldWrap.isValidValue(null));
      assertThrows(Exception.class, () -> fieldWrap.toString(null));
      assertThrows(Exception.class, () -> fieldWrap.toValue(null));
    }
  }

  @Test
  void testSliderFailPanel() {
    SliderFailParam sliderFailParam = new SliderFailParam();
    assertThrows(Exception.class, () -> PanelFieldsEditor.splits(sliderFailParam));
  }
}
