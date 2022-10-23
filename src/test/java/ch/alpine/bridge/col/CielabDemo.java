// code by jph
package ch.alpine.bridge.col;

import java.awt.Dimension;
import java.io.IOException;

import ch.alpine.bridge.fig.ListPlot;
import ch.alpine.bridge.fig.Show;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.alg.Subdivide;
import ch.alpine.tensor.ext.HomeDirectory;

public enum CielabDemo {
  ;
  public static void main(String[] args) throws IOException {
    Tensor domain = Subdivide.of(0, 1, 200);
    Show show = new Show();
    show.add(new ListPlot(domain.map(Cielabf::forward), domain));
    show.add(new ListPlot(domain, domain.map(Cielabf::inverse)));
    show.export(HomeDirectory.Pictures("cielab.png"), new Dimension(400, 400));
  }
}
