// code by jph
package ch.alpine.bridge.swing;

import javax.swing.JMenu;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class LookAndFeelsTest {
  @Test
  void testSimple() {
    for (LookAndFeelInfo lookAndFeelInfo : UIManager.getInstalledLookAndFeels())
      try {
        UIManager.setLookAndFeel(lookAndFeelInfo.getClassName());
      } catch (Exception exception) {
        exception.printStackTrace();
      }
  }

  @ParameterizedTest
  @EnumSource(LookAndFeels.class)
  void testUpdateUI(LookAndFeels lookAndFeels) {
    try {
      lookAndFeels.updateUI();
      JMenu jMenu = new JMenu();
      jMenu.getForeground();
    } catch (Exception exception) {
      exception.printStackTrace();
    }
  }
}
