// code by jph
package ch.alpine.bridge.fig;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.WindowConstants;

import ch.alpine.bridge.awt.ScreenRectangles;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.ext.HomeDirectory;
import ch.alpine.tensor.sca.Ceiling;
import ch.alpine.tensor.sca.Round;
import ch.alpine.tensor.sca.pow.Sqrt;

public class ShowDialog extends JDialog {
  /** non-blocking
   * 
   * @param shows
   * @return */
  public static JDialog of(Show... shows) {
    return of(List.of(shows));
  }

  public static JDialog of(List<Show> list) {
    ShowDialog showDialog = new ShowDialog(null, list);
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
      jButton.addActionListener(event -> {
        for (Show show : list)
          try {
            show.export(HomeDirectory.Pictures("fig_" + show.getPlotLabel() + ".png"), new Dimension(640, 480));
          } catch (Exception e) {
            e.printStackTrace();
          }
      });
      jToolBar.add(jButton);
      jPanel.add(BorderLayout.NORTH, jToolBar);
    }
    {
      // TODO BRIDGE extract below computation to separate function
      Scalar sqrt = Sqrt.FUNCTION.apply(RealScalar.of(list.size()));
      int cols = Round.intValueExact(sqrt);
      int rows = Ceiling.intValueExact(sqrt);
      JPanel grid = new JPanel(new GridLayout(rows, cols));
      for (Show show : list) {
        ShowComponent showComponent = new ShowComponent();
        showComponent.setShow(show);
        grid.add(showComponent);
      }
      jPanel.add(BorderLayout.CENTER, grid);
      setContentPane(jPanel);
    }
    setSize(1000, 1000);
    setLocationRelativeTo(parentComponent);
    ScreenRectangles.create().placement(this);
  }
}
