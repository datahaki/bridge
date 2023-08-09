// code by jph
package ch.alpine.bridge.ref.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Field;

import org.junit.jupiter.api.Test;

import ch.alpine.bridge.ref.ex.GuiExtension;

class FieldToolTipTest {
  @Test
  void test() throws NoSuchFieldException, SecurityException {
    Field field = GuiExtension.class.getField("clipSlider");
    String string = FieldToolTip.of(field);
    assertEquals(string, "min=0, max=5");
  }
}
