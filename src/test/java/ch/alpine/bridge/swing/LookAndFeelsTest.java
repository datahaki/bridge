// code by jph
package ch.alpine.bridge.swing;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import org.junit.jupiter.api.Test;

class LookAndFeelsTest {
  @Test
  void testSimple() {
    for (LookAndFeelInfo lookAndFeelInfo : UIManager.getInstalledLookAndFeels())
      try {
        // System.out.println(lookAndFeelInfo);
        UIManager.setLookAndFeel(lookAndFeelInfo.getClassName());
      } catch (Exception exception) {
        throw new RuntimeException(exception);
      }
  }

  @Test
  void testUpdateUI() {
    for (LookAndFeels lookAndFeels : LookAndFeels.standard()) {
      JFrame jFrame = new JFrame();
      jFrame.setVisible(true);
      lookAndFeels.updateComponentTreeUI();
      JMenu jMenu = new JMenu();
      jMenu.getForeground();
      jFrame.setVisible(false);
      jFrame.dispose();
    }
  }
}
