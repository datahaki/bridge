// code by jph
package ch.alpine.bridge.swing;

import java.awt.Font;

import org.junit.jupiter.api.Test;

class FontParamTest {
  @Test
  void test() {
    FontParam fontParam = new FontParam(new Font(Font.DIALOG_INPUT, Font.ITALIC, 22));
    fontParam.toFont();
  }
}
