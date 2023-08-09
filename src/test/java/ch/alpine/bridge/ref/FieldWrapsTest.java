// code by jph
package ch.alpine.bridge.ref;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;

import org.junit.jupiter.api.Test;

import ch.alpine.bridge.ref.ex.GuiExtension;
import ch.alpine.tensor.mat.re.Pivots;

class FieldWrapsTest {
  @Test
  void test() {
    assertTrue(FieldWraps.INSTANCE.elemental(Pivots.class));
    assertTrue(FieldWraps.INSTANCE.elemental(String.class));
  }

  @Test
  void testSimple() {
    for (Field field : GuiExtension.class.getFields())
      FieldWraps.INSTANCE.wrap(field);
  }
}
