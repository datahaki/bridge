// code by jph
package ch.alpine.java.ref.gui;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import junit.framework.TestCase;

public class ConfigPanelTest extends TestCase {
  public void testSimple() throws InterruptedException {
    GuiExtension guiExtension = new GuiExtension();
    JComponent jComponent = ConfigPanel.of(guiExtension).getFieldsAndTextarea();
    // ---
    JFrame jFrame = new JFrame();
    jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    jFrame.setContentPane(jComponent);
    jFrame.setBounds(500, 200, 500, 500);
    jFrame.setVisible(true);
    Thread.sleep(200);
    jFrame.setVisible(false);
  }
}
