// code by jph
package ch.alpine.bridge.col;

import java.awt.Dimension;
import java.io.IOException;

import ch.alpine.bridge.fig.ChartUtils;
import ch.alpine.bridge.fig.JFreeChart;
import ch.alpine.bridge.fig.ListPlot;
import ch.alpine.bridge.fig.VisualSet;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.alg.Subdivide;
import ch.alpine.tensor.ext.HomeDirectory;

public enum CielabDemo {
  ;
  public static void main(String[] args) throws IOException {
    Tensor domain = Subdivide.of(0, 1, 200);
    VisualSet visualSet = new VisualSet();
    visualSet.add(domain.map(Cielabf::forward), domain);
    visualSet.add(domain, domain.map(Cielabf::inverse));
    JFreeChart jFreeChart = ListPlot.of(visualSet, true);
    ChartUtils.saveChartAsPNG(HomeDirectory.Pictures("cielab.png"), jFreeChart, new Dimension(400, 400));
  }
}
