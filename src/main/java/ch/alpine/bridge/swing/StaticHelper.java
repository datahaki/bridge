// code by jph
package ch.alpine.bridge.swing;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/* package */ enum StaticHelper {
  ;
  public static final int WINDOW_MARGIN_WIDTH = 20;
  public static final int WINDOW_MARGIN_HEIGHT = 60;

  public static Color alpha064(Color color) {
    return new Color(color.getRed(), color.getGreen(), color.getBlue(), 64);
  }

  public static Color alpha128(Color color) {
    return new Color(color.getRed(), color.getGreen(), color.getBlue(), 128);
  }

  static void configure(JDialog jDialog, JPanel jPanel) {
    jDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    jDialog.setContentPane(jPanel);
    Dimension dimension = jPanel.getPreferredSize();
    jDialog.setSize( //
        StaticHelper.WINDOW_MARGIN_WIDTH + dimension.width, //
        StaticHelper.WINDOW_MARGIN_HEIGHT + dimension.height);
    jDialog.setResizable(false);
  }
}
