// code by jph
package ch.alpine.java.ref;

import java.util.List;
import java.util.Properties;

import ch.alpine.tensor.mat.re.Pivots;
import junit.framework.TestCase;

public class EnumFieldWrapTest extends TestCase {
  public void testEnum() {
    GuiExtension guiExtension = new GuiExtension();
    List<String> list = ObjectProperties.list(guiExtension);
    assertTrue(list.contains("pivots=ARGMAX_ABS"));
  }

  public void testProperties() {
    Properties properties = new Properties();
    properties.setProperty("key", "SOMETHING_WITH_UNDERSCORES");
    String string = properties.getProperty("key");
    assertEquals(string, "SOMETHING_WITH_UNDERSCORES");
  }

  public void testEnumProp() {
    Properties properties = ObjectProperties.properties(new GuiExtension());
    String string = properties.getProperty("pivots");
    assertEquals(string, "ARGMAX_ABS");
    properties.setProperty("pivots", "doesnotexist");
    GuiExtension guiExtension = new GuiExtension();
    guiExtension.pivots = Pivots.FIRST_NON_ZERO;
    ObjectProperties.set(guiExtension, properties);
    assertEquals(guiExtension.pivots, Pivots.FIRST_NON_ZERO);
  }
}
