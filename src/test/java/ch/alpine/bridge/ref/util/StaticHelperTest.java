// code by jph
package ch.alpine.bridge.ref.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Field;

import org.junit.jupiter.api.Test;

import ch.alpine.bridge.ref.ex.GuiExtension;

class StaticHelperTest {
  @Test
  void testAll() {
    GuiExtension guiExtension = new GuiExtension();
    for (Field field : guiExtension.getClass().getFields())
      StaticHelper.get(field, guiExtension);
  }

  @Test
  void testSingle() throws NoSuchFieldException, SecurityException {
    Field field = GuiExtension.class.getField("function");
    GuiExtension guiExtension = new GuiExtension();
    Object object = StaticHelper.get(field, guiExtension);
    assertEquals(object, "abc");
  }
}
