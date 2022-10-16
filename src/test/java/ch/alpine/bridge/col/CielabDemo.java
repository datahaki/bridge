// code by jph
package ch.alpine.bridge.col;

import java.awt.Dimension;
import java.io.IOException;

import ch.alpine.bridge.fig.Showable;
import ch.alpine.bridge.fig.ListPlot;
import ch.alpine.bridge.fig.Show;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.alg.Subdivide;
import ch.alpine.tensor.ext.HomeDirectory;

public enum CielabDemo {
  ;
  public static void main(String[] args) throws IOException {
    Tensor domain = Subdivide.of(0, 1, 200);
    Show visualSet = new Show();
    visualSet.add(domain.map(Cielabf::forward), domain);
    visualSet.add(domain, domain.map(Cielabf::inverse));
    Showable jFreeChart = ListPlot.of(visualSet.setJoined(true));
    Show.export(HomeDirectory.Pictures("cielab.png"), jFreeChart, new Dimension(400, 400));
  }
}
