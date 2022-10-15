// code by jph
package demo.tensor.pdf;

import java.awt.Dimension;
import java.io.IOException;

import ch.alpine.bridge.fig.ChartUtils;
import ch.alpine.bridge.fig.JFreeChart;
import ch.alpine.bridge.fig.ListPlot;
import ch.alpine.bridge.fig.VisualSet;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.alg.Subdivide;
import ch.alpine.tensor.ext.HomeDirectory;
import ch.alpine.tensor.sca.erf.Erfc;

public enum ErfcDemo {
  ;
  public static void main(String[] args) throws IOException {
    Tensor domain = Subdivide.of(-5, 5, 300);
    VisualSet visualSet = new VisualSet();
    visualSet.add(domain, domain.map(Erfc.FUNCTION));
    JFreeChart jFreeChart = ListPlot.of(visualSet);
    ChartUtils.saveChartAsPNG(HomeDirectory.Pictures(Erfc.class.getSimpleName() + ".png"), jFreeChart, //
        new Dimension(640, 480));
  }
}
