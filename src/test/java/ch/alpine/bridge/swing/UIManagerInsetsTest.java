// code by jph
package ch.alpine.bridge.swing;

import java.util.Objects;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class UIManagerInsetsTest {
  @BeforeAll
  static void beforeAll() {
    LookAndFeels.DEFAULT.updateComponentTreeUI();
  }

  @ParameterizedTest
  @EnumSource
  void testSimple(UIManagerInsets uiManagerInsets) {
    Objects.requireNonNull(uiManagerInsets.get());
  }
}
