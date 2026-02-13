// code by jph
package ch.alpine.bridge.fig;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import ch.alpine.bridge.awt.ScreenRectangles;

public enum ShowWindow {
  ;
  private static final int SIZE = 800;

  /** non-blocking
   * 
   * @param shows
   * @return */
  public static JDialog asDialog(Show... shows) {
    return asDialog(List.of(shows));
  }

  /** non-blocking
   * 
   * @param list
   * @return */
  public static JDialog asDialog(List<Show> list) {
    Component parentComponent = null;
    /* false -> non-modal == non-blocking */
    JDialog jDialog = new JDialog(JOptionPane.getFrameForComponent(parentComponent), false);
    jDialog.setContentPane(createContainer(list));
    jDialog.setSize(SIZE, SIZE);
    jDialog.setLocationRelativeTo(parentComponent);
    jDialog.setTitle(StaticHelper.defaultTitle());
    jDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    ScreenRectangles.create().placement(jDialog);
    jDialog.setVisible(true);
    return jDialog;
  }

  public static JFrame asFrame(Show... shows) {
    return asFrame(List.of(shows));
  }

  public static JFrame asFrame(List<Show> list) {
    Component parentComponent = null;
    JFrame jFrame = new JFrame();
    jFrame.setContentPane(createContainer(list));
    jFrame.setSize(SIZE, SIZE);
    jFrame.setLocationRelativeTo(parentComponent);
    jFrame.setTitle(StaticHelper.defaultTitle());
    jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    ScreenRectangles.create().placement(jFrame);
    jFrame.setVisible(true);
    return jFrame;
  }

  private static Container createContainer(List<Show> list) {
    JPanel contentPane = new JPanel(new BorderLayout());
    JComponent jComponent = ShowGridComponent.of(list);
    contentPane.add(BorderLayout.CENTER, jComponent);
    contentPane.add(BorderLayout.NORTH, StaticHelper.createToolbar(jComponent));
    return contentPane;
  }
}
