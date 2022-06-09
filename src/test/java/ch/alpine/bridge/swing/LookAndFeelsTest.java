// code by jph
package ch.alpine.bridge.swing;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.swing.JMenu;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class LookAndFeelsTest {
  @Test
  public void testSimple() {
    for (LookAndFeelInfo lookAndFeelInfo : UIManager.getInstalledLookAndFeels())
      try {
        UIManager.setLookAndFeel(lookAndFeelInfo.getClassName());
      } catch (Exception exception) {
        exception.printStackTrace();
      }
  }

  @ParameterizedTest
  @EnumSource(LookAndFeels.class)
  public void testUpdateUI(LookAndFeels lookAndFeels) throws Exception {
    lookAndFeels.updateUI();
    JMenu jMenu = new JMenu();
    System.out.println(lookAndFeels + "  " + jMenu.getForeground());
  }

  @Test
  public void testIntro() throws Exception {
    boolean flag = false;
    for (LookAndFeels lookAndFeels : LookAndFeels.values()) {
      lookAndFeels.updateUI();
      assertEquals(UIManager.getInt("asd"), flag ? 123 : 0);
      UIDefaults uiDefaults = UIManager.getDefaults();
      assertEquals(uiDefaults.getInt("asd"), flag ? 123 : 0);
      uiDefaults.put("asd", 123);
      flag = true;
      int int1 = uiDefaults.getInt("asd");
      assertEquals(int1, 123);
      int int2 = UIManager.getInt("asd");
      assertEquals(int2, 123);
    }
  }
}
