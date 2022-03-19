// code by jph
package ch.alpine.java.ref;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.StringReader;
import java.util.Properties;

import org.junit.jupiter.api.Test;

import ch.alpine.java.ref.util.PanelFieldsEditor;

public class GuiExtensionTest {
  @Test
  public void testSimple() {
    GuiExtension guiExtension = new GuiExtension();
    PanelFieldsEditor fieldsPanel = new PanelFieldsEditor(guiExtension);
    fieldsPanel.addUniversalListener(() -> System.out.println("changed"));
    fieldsPanel.createJScrollPane();
  }

  @Test
  public void testReader() throws IOException {
    GuiExtension guiExtension = new GuiExtension();
    String string = ObjectProperties.string(guiExtension);
    Properties properties = new Properties();
    try (StringReader stringReader = new StringReader(string)) {
      properties.load(stringReader);
    }
    assertTrue(23 < properties.entrySet().size());
  }
  // public void testGetMethod() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
  // Method method = GuiExtension.class.getMethod(PanelFieldsEditor.method("string"));
  // GuiExtension guiExtension = new GuiExtension();
  // {
  // List<String> list = (List<String>) method.invoke(guiExtension);
  // assertTrue(list.contains("a"));
  // assertTrue(list.contains("abc"));
  // }
  // guiExtension.string = "123";
  // {
  // List<String> list = (List<String>) method.invoke(guiExtension);
  // assertTrue(list.contains("a"));
  // assertFalse(list.contains("abc"));
  // assertTrue(list.contains("123"));
  // }
  // }

  @Test
  public void testGetMethodFail() {
    assertThrows(Exception.class, () -> GuiExtension.class.getMethod("stringValues2"));
  }
}
