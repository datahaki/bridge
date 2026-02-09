// code by jph
package ch.alpine.bridge.fig;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.nio.file.Path;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.WindowConstants;

import ch.alpine.bridge.awt.ScreenRectangles;
import ch.alpine.bridge.io.SanitizeFilename;
import ch.alpine.tensor.ext.HomeDirectory;

// TODO BRIDGE cannot easily go fullscreen etc...
public class ShowWindow extends JDialog {
  private static final int SIZE = 800;

  /** non-blocking
   * 
   * @param shows
   * @return */
  public static JDialog of(Show... shows) {
    return of(List.of(shows));
  }

  public static JDialog of(List<Show> list) {
    ShowWindow showDialog = new ShowWindow(null, list);
    ScreenRectangles.create().placement(showDialog);
    showDialog.setVisible(true);
    return showDialog;
  }

  // ---
  private ShowWindow(Component parentComponent, List<Show> list) {
    /* false -> non-modal == non-blocking */
    super(JOptionPane.getFrameForComponent(parentComponent), false);
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
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
          Path path = HomeDirectory.Pictures.resolve(SanitizeFilename.of(string));
          Dimension dimension = showComponent.getSize();
          show.export(path, dimension);
        } catch (Exception exception) {
          exception.printStackTrace();
        }
      }
    });
    jToolBar.add(jButton);
    contentPane.add(BorderLayout.NORTH, jToolBar);
    setContentPane(contentPane);
    setSize(SIZE, SIZE);
    setLocationRelativeTo(parentComponent);
  }
}
