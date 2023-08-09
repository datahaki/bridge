// code by jph
package ch.alpine.bridge.swing;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class UIManagerBooleanTest {
  @ParameterizedTest
  @EnumSource
  void testSimple(UIManagerBoolean uiManagerBoolean) {
    uiManagerBoolean.getAsBoolean();
  }

  @Test
  void testLength() {
    assertEquals(UIManagerBoolean.values().length, 36);
  }
}
