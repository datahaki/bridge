// code by jph
package demo.tensor.sca;

import java.awt.Dimension;
import java.io.IOException;

import ch.alpine.bridge.fig.ArrayPlot;
import ch.alpine.bridge.fig.ChartUtils;
import ch.alpine.bridge.fig.JFreeChart;
import ch.alpine.bridge.fig.ListPlot;
import ch.alpine.bridge.fig.VisualSet;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.alg.Subdivide;
import ch.alpine.tensor.api.ScalarUnaryOperator;
import ch.alpine.tensor.ext.HomeDirectory;
import ch.alpine.tensor.img.ColorDataGradients;
import ch.alpine.tensor.io.Pretty;
import ch.alpine.tensor.mat.re.Inverse;
import ch.alpine.tensor.mat.re.LinearSolve;
import ch.alpine.tensor.sca.Round;
import ch.alpine.tensor.sca.ply.ChebyshevNodes;
import ch.alpine.tensor.sca.ply.ClenshawChebyshev;
import ch.alpine.tensor.sca.tri.Sin;

public enum ChebyshevApprox {
  ;
  public static void main(String[] args) throws IOException {
    int n = 7 + 7;
    ScalarUnaryOperator suo = x -> Sin.FUNCTION.apply(x.multiply(x).negate().add(x));
    {
      Tensor matrix = ChebyshevNodes._0.matrix(n);
      JFreeChart jFreeChart = ArrayPlot.of(matrix, ColorDataGradients.DENSITY);
      ChartUtils.saveChartAsPNG(HomeDirectory.Pictures(ChebyshevNodes.class.getSimpleName() + ".png"), jFreeChart, //
          new Dimension(600, 400));
    }
    {
      Tensor matrix = ChebyshevNodes._0.matrix(n);
      JFreeChart jFreeChart = ArrayPlot.of(Inverse.of(matrix), ColorDataGradients.DENSITY);
      ChartUtils.saveChartAsPNG(HomeDirectory.Pictures(ChebyshevNodes.class.getSimpleName() + "_inverse.png"), jFreeChart, //
          new Dimension(600, 400));
    }
    Tensor domain = Subdivide.of(-1, 1, 100);
    VisualSet visualSet = new VisualSet();
    for (ChebyshevNodes chebyshevNodes : ChebyshevNodes.values()) {
      Tensor coeffs = LinearSolve.of(chebyshevNodes.matrix(n), chebyshevNodes.of(n).map(suo));
      System.out.println(Pretty.of(coeffs.map(Round._3)));
      Tensor error = domain.map(ClenshawChebyshev.of(coeffs)).subtract(domain.map(suo));
      visualSet.add(domain, error);
    }
    JFreeChart jFreeChart = ListPlot.of(visualSet.setJoined(true));
    ChartUtils.saveChartAsPNG(HomeDirectory.Pictures(ChebyshevApprox.class.getSimpleName() + ".png"), jFreeChart, //
        new Dimension(600, 400));
  }
}
