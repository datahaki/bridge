// code by jph
package ch.alpine.bridge.swing;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class CheckBoxIconTest {
  @ParameterizedTest
  @EnumSource
  void test(CheckBoxIcon checkBoxIcons) {
    checkBoxIcons.create(32, false);
    checkBoxIcons.create(32, true);
  }
}
