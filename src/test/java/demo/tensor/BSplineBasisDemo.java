package demo.tensor;

import javax.swing.JDialog;

import ch.alpine.bridge.fig.Plot;
import ch.alpine.bridge.fig.Show;
import ch.alpine.bridge.fig.ShowDialog;
import ch.alpine.bridge.fig.Showable;
import ch.alpine.tensor.itp.BSplineBasis;
import ch.alpine.tensor.sca.Clip;
import ch.alpine.tensor.sca.Clips;

public enum BSplineBasisDemo {
  ;
  public static void main(String[] args) {
    Clip clip = Clips.absolute(3);
    Show show = new Show();
    show.setPlotLabel("BSplineBasis");
    for (int d = 0; d < 6; ++d) {
      Showable showable = show.add(Plot.of(BSplineBasis.of(d), clip));
      showable.setLabel("deg " + d);
    }
    JDialog showDialog = ShowDialog.of(show);
    showDialog.setVisible(true);
  }
}
