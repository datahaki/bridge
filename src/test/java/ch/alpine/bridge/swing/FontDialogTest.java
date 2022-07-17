// code by jph
package ch.alpine.bridge.swing;

import java.awt.Font;

import javax.swing.JTextField;

import org.junit.jupiter.api.Test;

import ch.alpine.bridge.swing.FontDialog.Param;

class FontDialogTest {
  @Test
  void test() {
    FontDialog fontDialog = new FontDialog(null, new Font(Font.DIALOG, Font.BOLD, 4), e -> {
      // ---
    });
    fontDialog.getTitle();
  }

  @Test
  void test2() {
    Param fontParam = new Param(new Font(Font.DIALOG_INPUT, Font.ITALIC, 22));
    fontParam.toFont();
  }

  @Test
  void testSimple() {
    LookAndFeels.LIGHT.updateComponentTreeUI();
    Font font = new JTextField().getFont(); // "Cantarell"
    Param fontParam = new Param(font);
    fontParam.toFont();
  }

  @Test
  void testUnknown() {
    Font font = new Font("asdfarefevfcsdzvfasdf", Font.PLAIN, 12);
    font.getName();
  }
}
