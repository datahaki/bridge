// code by jph
package demo.tensor.sca;

import java.awt.Dimension;
import java.io.IOException;

import ch.alpine.bridge.fig.ChartUtils;
import ch.alpine.bridge.fig.JFreeChart;
import ch.alpine.bridge.fig.ListPlot;
import ch.alpine.bridge.fig.VisualSet;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.alg.Subdivide;
import ch.alpine.tensor.alg.UnitVector;
import ch.alpine.tensor.api.ScalarUnaryOperator;
import ch.alpine.tensor.ext.HomeDirectory;
import ch.alpine.tensor.sca.ply.Chebyshev;
import ch.alpine.tensor.sca.ply.ClenshawChebyshev;

public enum ChebyshevDemo {
  ;
  public static void main(String[] args) throws IOException {
    int max = 6;
    {
      Tensor domain = Subdivide.of(-1., 1., 30);
      VisualSet visualSet = new VisualSet();
      for (int d = 0; d < max; ++d) {
        ScalarUnaryOperator suo = ClenshawChebyshev.of(UnitVector.of(d + 1, d));
        ScalarUnaryOperator su2 = Chebyshev.T.of(d);
        visualSet.add(domain, domain.map(suo).subtract(domain.map(su2)));
      }
      JFreeChart jFreeChart = ListPlot.of(visualSet, true);
      ChartUtils.saveChartAsPNG(HomeDirectory.Pictures(ChebyshevDemo.class.getSimpleName() + ".png"), jFreeChart, new Dimension(600, 400));
    }
    {
      Tensor domain = Subdivide.of(-1., 1., 30);
      VisualSet visualSet = new VisualSet();
      for (int d = 0; d < max; ++d) {
        ScalarUnaryOperator suo = Chebyshev.T.of(d);
        visualSet.add(domain, domain.map(suo));
      }
      visualSet.setPlotLabel("Chebyshev Polynomials");
      JFreeChart jFreeChart = ListPlot.of(visualSet, true);
      ChartUtils.saveChartAsPNG(HomeDirectory.Pictures(Chebyshev.class.getSimpleName() + "T.png"), jFreeChart, new Dimension(600, 400));
    }
    {
      Tensor domain = Subdivide.of(-1., 1., 30);
      VisualSet visualSet = new VisualSet();
      for (int d = 0; d < max; ++d) {
        ScalarUnaryOperator suo = Chebyshev.U.of(d);
        visualSet.add(domain, domain.map(suo));
      }
      visualSet.setPlotLabel("Chebyshev Polynomials");
      JFreeChart jFreeChart = ListPlot.of(visualSet, true);
      ChartUtils.saveChartAsPNG(HomeDirectory.Pictures(Chebyshev.class.getSimpleName() + "U.png"), jFreeChart, new Dimension(600, 400));
    }
  }
}
