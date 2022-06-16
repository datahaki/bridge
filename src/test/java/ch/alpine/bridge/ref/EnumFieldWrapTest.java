// code by jph
package ch.alpine.bridge.ref;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Properties;

import org.junit.jupiter.api.Test;

import ch.alpine.tensor.mat.re.Pivots;

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

  @Test
  void testEnumProp() {
    Properties properties = DeprecatedObjProp.properties(new GuiExtension());
    String string = properties.getProperty("pivots");
    assertEquals(string, "ARGMAX_ABS");
    properties.setProperty("pivots", "doesnotexist");
    GuiExtension guiExtension = new GuiExtension();
    guiExtension.pivots = Pivots.FIRST_NON_ZERO;
    ObjectProperties.set(guiExtension, properties);
    assertEquals(guiExtension.pivots, Pivots.FIRST_NON_ZERO);
  }
}
