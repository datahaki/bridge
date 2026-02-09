// code by jph
package ch.alpine.bridge.fig;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.lang.StackWalker.StackFrame;
import java.nio.file.Path;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.WindowConstants;

import ch.alpine.bridge.awt.ScreenRectangles;
import ch.alpine.bridge.lang.FriendlyFormat;
import ch.alpine.tensor.ext.HomeDirectory;

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
    jDialog.setTitle(defaultTitle());
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
    jFrame.setTitle(defaultTitle());
    jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    ScreenRectangles.create().placement(jFrame);
    jFrame.setVisible(true);
    return jFrame;
  }

  private static String defaultTitle() {
    StackWalker stackWalker = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);
    StackFrame stackFrame = stackWalker.walk(stream -> stream //
        .filter(sf -> !sf.getDeclaringClass().equals(ShowWindow.class)) //
        .filter(sf -> !sf.getDeclaringClass().isInterface()) //
        .findFirst() //
        .orElseThrow());
    // new ShortStackTrace("ch").print();
    return stackFrame.getDeclaringClass().getSimpleName();
  }

  private static Container createContainer(List<Show> list) {
    JPanel contentPane = new JPanel(new BorderLayout());
    JPanel center = ShowGridComponent.of(list);
    contentPane.add(BorderLayout.CENTER, center);
    JToolBar jToolBar = new JToolBar();
    jToolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
    jToolBar.setFloatable(false);
    JButton jButton = new JButton("export");
    jButton.addActionListener(_ -> {
      for (Component component : center.getComponents()) {
        ShowComponent showComponent = (ShowComponent) component;
        Show show = showComponent.getShow();
        try {
          String string = "fig_" + show.getPlotLabel() + ".png";
          Path path = HomeDirectory.Pictures.resolve(FriendlyFormat.sanitize(string));
          Dimension dimension = showComponent.getSize();
          show.export(path, dimension);
        } catch (Exception exception) {
          exception.printStackTrace();
        }
      }
    });
    jToolBar.add(jButton);
    contentPane.add(BorderLayout.NORTH, jToolBar);
    return contentPane;
  }
}
