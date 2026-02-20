// code by jph
package ch.alpine.bridge.col;

import ch.alpine.bridge.fig.ListLinePlot;
import ch.alpine.bridge.fig.Plot;
import ch.alpine.bridge.fig.Show;
import ch.alpine.bridge.pro.ShowProvider;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.alg.Subdivide;
import ch.alpine.tensor.sca.Clip;
import ch.alpine.tensor.sca.Clips;

class CielabDemo implements ShowProvider {
  @Override
  public Show getShow() {
    Clip clip = Clips.unit();
    Tensor domain = Subdivide.increasing(clip, 50);
    Show show = new Show();
    show.setPlotLabel("Cielabf");
    show.add(ListLinePlot.of(domain.maps(Cielabf::forward), domain));
    show.add(Plot.of(Cielabf::inverse, clip));
    return show;
  }

  static void main() {
    new CielabDemo().runStandalone();
  }
}
