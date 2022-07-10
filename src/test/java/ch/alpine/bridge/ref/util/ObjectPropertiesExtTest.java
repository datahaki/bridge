// code by jph
package ch.alpine.bridge.ref.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Properties;

import org.junit.jupiter.api.Test;

import ch.alpine.bridge.ref.ex.ClipParam;
import ch.alpine.bridge.ref.ex.GuiExtension;
import ch.alpine.bridge.ref.ex.V011Param;
import ch.alpine.tensor.mat.re.Pivots;
import ch.alpine.tensor.qty.Quantity;
import ch.alpine.tensor.sca.Clips;

class ObjectPropertiesExtTest {
  @Test
  void testSimple() {
    V011Param v011Param = new V011Param(3);
    v011Param.list.set(1, null);
    v011Param.another.set(1, null);
    ObjectProperties.list(v011Param);
    ObjectProperties.join(v011Param);
    // System.out.println();
    Properties properties = ObjectPropertiesExt.properties(v011Param);
    ObjectProperties.set(new V011Param(1), properties);
    ObjectProperties.set(new V011Param(2), properties);
  }

  @Test
  void testRecreate() {
    ClipParam clipParam = new ClipParam();
    clipParam.clipReal = Clips.interval(20, 21);
    Properties properties = ObjectPropertiesExt.properties(clipParam);
    properties.put("clipMeter", "{9[m], 10[m]}");
    ClipParam clipParam2 = ObjectProperties.set(new ClipParam(), properties);
    assertEquals(clipParam2.clipReal, Clips.interval(20, 21));
    assertEquals(clipParam2.clipMeter, Clips.interval(Quantity.of(9, "m"), Quantity.of(10, "m")));
  }

  @Test
  void testEnumProp() {
    Properties properties = ObjectPropertiesExt.properties(new GuiExtension());
    String string = properties.getProperty("pivots");
    assertEquals(string, "ARGMAX_ABS");
    properties.setProperty("pivots", "doesnotexist");
    GuiExtension guiExtension = new GuiExtension();
    guiExtension.pivots = Pivots.FIRST_NON_ZERO;
    ObjectProperties.set(guiExtension, properties);
    assertEquals(guiExtension.pivots, Pivots.FIRST_NON_ZERO);
  }
}
