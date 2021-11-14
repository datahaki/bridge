// code by jph
package ch.alpine.javax.swing;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import junit.framework.TestCase;

public class LookAndFeelsTest extends TestCase {
  public void testSimple() {
    for (LookAndFeelInfo lookAndFeelInfo : UIManager.getInstalledLookAndFeels()) {
      // System.out.println(lookAndFeelInfo);
      try {
        UIManager.setLookAndFeel(lookAndFeelInfo.getClassName());
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
