// code by jph
package ch.alpine.bridge.pro;

import java.awt.BorderLayout;
import java.awt.Container;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import ch.alpine.bridge.awt.AwtUtil;
import ch.alpine.bridge.fig.Show;
import ch.alpine.bridge.fig.ShowGridComponent;

public enum ShowWindow {
  ;
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
    /* false -> non-modal == non-blocking */
    JDialog jDialog = new JDialog(JOptionPane.getFrameForComponent(null), false);
    jDialog.setContentPane(createContainer(list));
    jDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    return jDialog;
  }

  public static JFrame asFrame(Show... shows) {
    return asFrame(List.of(shows));
  }

  public static JFrame asFrame(List<Show> list) {
    JFrame jFrame = new JFrame();
    jFrame.setContentPane(createContainer(list));
    jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    return jFrame;
  }

  private static Container createContainer(List<Show> list) {
    JPanel contentPane = new JPanel(new BorderLayout());
    JComponent jComponent = ShowGridComponent.of(list);
    contentPane.add(BorderLayout.CENTER, jComponent);
    contentPane.add(BorderLayout.NORTH, AwtUtil.createToolbar(jComponent));
    return contentPane;
  }
}
