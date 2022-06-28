// code by jph
package ch.alpine.bridge.ref;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.Properties;

import org.junit.jupiter.api.Test;

import ch.alpine.bridge.ref.util.PanelFieldsEditor;

class GuiExtensionTest {
  @Test
  void testSimple() {
    GuiExtension guiExtension = new GuiExtension();
    PanelFieldsEditor fieldsPanel = new PanelFieldsEditor(guiExtension);
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
  void testReader() throws IOException {
    GuiExtension guiExtension = new GuiExtension();
    String string = ObjectProperties.join(guiExtension);
    Properties properties = new Properties();
    try (StringReader stringReader = new StringReader(string)) {
      properties.load(stringReader);
    }
    assertTrue(23 < properties.entrySet().size());
  }

  @Test
  void testGetMethodFail() {
    assertThrows(Exception.class, () -> GuiExtension.class.getMethod("stringValues2"));
  }
}
