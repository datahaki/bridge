// code by jph
package demo.tensor.pdf;

import java.awt.Color;
import java.io.IOException;

import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;

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
    JFreeChart jFreeChart = ListPlot.of(visualSet, true);
    jFreeChart.setBackgroundPaint(Color.WHITE);
    ChartUtils.saveChartAsPNG(HomeDirectory.Pictures(Erfc.class.getSimpleName() + ".png"), jFreeChart, 640, 480);
  }
}
