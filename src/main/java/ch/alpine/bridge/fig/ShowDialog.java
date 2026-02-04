// code by jph
package ch.alpine.bridge.fig;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.WindowConstants;

import ch.alpine.bridge.awt.ScreenRectangles;
import ch.alpine.tensor.ext.HomeDirectory;

// TODO BRIDGE cannot easily go fullscreen etc...
public class ShowDialog extends JDialog {
  private static final int SIZE = 800;

  /** non-blocking
   * 
   * @param shows
   * @return */
  public static JDialog of(Show... shows) {
    return of(List.of(shows));
  }

  public static JDialog of(List<Show> list) {
    ShowDialog showDialog = new ShowDialog(null, list);
    ScreenRectangles.create().placement(showDialog); // TODO BRIDGE redundant !?
    showDialog.setVisible(true);
    return showDialog;
  }

  // ---
  public ShowDialog(Component parentComponent, List<Show> list) {
    super(JOptionPane.getFrameForComponent(parentComponent), false); // non-blocking
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    JPanel jPanel = new JPanel(new BorderLayout());
    {
      JToolBar jToolBar = new JToolBar();
      jToolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
      jToolBar.setFloatable(false);
      JButton jButton = new JButton("export");
      jButton.addActionListener(_ -> {
        for (Show show : list)
          try {
            // TODO BRIDGE label to filename
            // TODO BRIDGE dimension from dialog
            show.export(HomeDirectory.Pictures("fig_" + show.getPlotLabel() + ".png"), new Dimension(640, 480));
          } catch (Exception exception) {
            exception.printStackTrace();
          }
      });
      jToolBar.add(jButton);
      jPanel.add(BorderLayout.NORTH, jToolBar);
    }
    {
      
      jPanel.add(BorderLayout.CENTER, ShowGridComponent.of(list));
    }
    setContentPane(jPanel);
    setSize(SIZE, SIZE);
    setLocationRelativeTo(parentComponent);
    ScreenRectangles.create().placement(this);
  }
}
