// code by jph
package ch.alpine.bridge.swing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Font;
import java.util.List;

import javax.swing.JTextField;

import org.junit.jupiter.api.Test;

class FontParamTest {
  @Test
  void test() {
    FontParam fontParam = new FontParam(new Font(Font.DIALOG_INPUT, Font.ITALIC, 22));
    fontParam.toFont();
  }

  @Test
  void testSimple() {
    LookAndFeels.LIGHT.updateComponentTreeUI();
    Font font = new JTextField().getFont(); // "Cantarell"
    FontParam fontParam = new FontParam(font);
    Font font2 = fontParam.toFont();
    assertEquals(font2.getName(), "Cantarell");
  }

  @Test
  void testUnknown() {
    Font font = new Font("asdfarefevfcsdzvfasdf", Font.PLAIN, 12);
    font.getName();
  }

  @Test
  void testNames() {
    List<String> list = FontParam.names();
    for (FontName fontName : FontName.values())
      assertTrue(list.contains(fontName.name()));
  }
}
