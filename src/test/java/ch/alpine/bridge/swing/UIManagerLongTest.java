// code by jph
package ch.alpine.bridge.swing;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class UIManagerLongTest {
  @ParameterizedTest
  @EnumSource
  void testSimple(UIManagerLong uiManagerLong) {
    uiManagerLong.getAsLong();
  }
}
