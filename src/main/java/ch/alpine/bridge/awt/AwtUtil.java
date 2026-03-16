// code by jph
package ch.alpine.bridge.awt;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRootPane;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;

/** Abstract Window Toolkit Utilities */
public enum AwtUtil {
  ;
  public static Point center(Dimension dimension) {
    return new Point(dimension.width / 2, dimension.height / 2);
  }

  public static JLabel iconAsLabel(Icon icon) {
    return new FitIconLabel(icon);
  }

  public static void addSeparator(JToolBar jToolBar) {
    // some look and feels introduce a vertical line | as separator...
    // jToolBar.addSeparator();
    // jToolBar.add(new JLabel("\u2000"));
    jToolBar.add(new JLabel("\u2000"));
  }

  public static Color withAlpha(Color color, int alpha) {
    return new Color( //
        color.getRed(), //
        color.getGreen(), //
        color.getBlue(), //
        alpha);
  }

  /** @param jFrame */
  public static void ctrlW(JFrame jFrame) {
    ctrlW(jFrame.getRootPane(), jFrame::dispose);
  }

  /** @param jDialog */
  public static void ctrlW(JDialog jDialog) {
    ctrlW(jDialog.getRootPane(), jDialog::dispose);
  }

  private static void ctrlW(JRootPane rootPane, Runnable runnable) {
    KeyStroke ctrlW = KeyStroke.getKeyStroke(KeyEvent.VK_W, KeyEvent.CTRL_DOWN_MASK);
    String actionMapKey = "closeWindow";
    rootPane.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(ctrlW, actionMapKey);
    rootPane.getActionMap().put(actionMapKey, new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        runnable.run();
      }
    });
  }
}
