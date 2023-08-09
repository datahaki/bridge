// code by jph
package ch.alpine.bridge.swing;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class UIManagerIntTest {
  @ParameterizedTest
  @EnumSource
  void testSimple(UIManagerInt uiManagerInt) {
    uiManagerInt.getAsInt();
  }
}
