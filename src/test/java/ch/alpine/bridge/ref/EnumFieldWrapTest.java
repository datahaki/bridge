// code by jph
package ch.alpine.bridge.ref;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Properties;

import org.junit.jupiter.api.Test;

import ch.alpine.bridge.ref.util.ObjectProperties;

class EnumFieldWrapTest {
  @Test
  void testEnum() {
    GuiExtension guiExtension = new GuiExtension();
    List<String> list = ObjectProperties.list(guiExtension);
    assertTrue(list.contains("pivots=ARGMAX_ABS"));
  }

  @Test
  void testProperties() {
    Properties properties = new Properties();
    properties.setProperty("key", "SOMETHING_WITH_UNDERSCORES");
    String string = properties.getProperty("key");
    assertEquals(string, "SOMETHING_WITH_UNDERSCORES");
  }
}
