// code by jph
package ch.alpine.bridge.swing;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class CheckBoxIconsTest {
  @ParameterizedTest
  @EnumSource
  void test(CheckBoxIcons checkBoxIcons) {
    checkBoxIcons.init(32);
  }
}
