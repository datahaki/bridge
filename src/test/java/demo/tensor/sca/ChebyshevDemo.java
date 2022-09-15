// code by jph
package demo.tensor.sca;

import java.awt.Color;
import java.io.IOException;

import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;

import ch.alpine.bridge.fig.ListPlot;
import ch.alpine.bridge.fig.VisualSet;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.alg.Subdivide;
import ch.alpine.tensor.alg.UnitVector;
import ch.alpine.tensor.api.ScalarUnaryOperator;
import ch.alpine.tensor.ext.HomeDirectory;
import ch.alpine.tensor.sca.bes.ChebyshevClenshaw;

public enum ChebyshevDemo {
  ;
  public static void main(String[] args) throws IOException {
    Tensor domain = Subdivide.of(-1., 1., 30);
    VisualSet visualSet = new VisualSet();
    for (int d = 0; d < 4; ++d) {
      ScalarUnaryOperator suo = ChebyshevClenshaw.of(UnitVector.of(d + 1, 0));
      visualSet.add(domain, domain.map(suo));
    }
    JFreeChart jFreeChart = ListPlot.of(visualSet, true);
    jFreeChart.setBackgroundPaint(Color.WHITE);
    ChartUtils.saveChartAsPNG(HomeDirectory.Pictures(ChebyshevDemo.class.getSimpleName() + ".png"), jFreeChart, 600, 400);
  }
}
