// code by jph
package ch.alpine.bridge.col;

import ch.alpine.bridge.fig.ListLinePlot;
import ch.alpine.bridge.fig.Plot;
import ch.alpine.bridge.fig.Show;
import ch.alpine.bridge.fig.ShowDialog;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.alg.Subdivide;
import ch.alpine.tensor.sca.Clip;
import ch.alpine.tensor.sca.Clips;

public enum CielabDemo {
  ;
  public static void main(String[] args) {
    Clip clip = Clips.unit();
    Tensor domain = Subdivide.increasing(clip, 50);
    Show show = new Show();
    show.setPlotLabel("Cielabf");
    show.add(ListLinePlot.of(domain.map(Cielabf::forward), domain));
    show.add(Plot.of(Cielabf::inverse, clip));
    ShowDialog.of(show);
  }
}
