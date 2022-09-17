// code by jph
package demo.tensor.sca;

import java.awt.Color;
import java.io.IOException;

import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;

import ch.alpine.bridge.fig.ArrayPlot;
import ch.alpine.bridge.fig.ListPlot;
import ch.alpine.bridge.fig.VisualRow;
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
import ch.alpine.tensor.sca.ply.ClenshawChebyshev;
import ch.alpine.tensor.sca.tri.Sin;

public enum ChebyshevApprox {
  ;
  public static void main(String[] args) throws IOException {
    int n = 16;
    ScalarUnaryOperator suo = x -> Sin.FUNCTION.apply(x.multiply(x).negate().add(x));
    Tensor nodes = ChebyshevNodes.of(n);
    Tensor rhs = nodes.map(suo);
    Tensor coeffs = LinearSolve.of(ChebyshevNodes.matrix(n), rhs);
    Tensor inverse = Inverse.of(ChebyshevNodes.matrix(n));
    {
      JFreeChart jFreeChart = ArrayPlot.of(inverse, ColorDataGradients.DENSITY);
      jFreeChart.setBackgroundPaint(Color.WHITE);
      ChartUtils.saveChartAsPNG(HomeDirectory.Pictures(ChebyshevNodes.class.getSimpleName() + ".png"), jFreeChart, 600, 400);
    }
    // ArrayPlot.of(null)
    System.out.println(Pretty.of(inverse.map(Round._3)));
    ScalarUnaryOperator approx = ClenshawChebyshev.of(coeffs);
    Tensor domain = Subdivide.of(-1, 1, 100);
    Tensor error = domain.map(approx).subtract(domain.map(suo));
    VisualSet visualSet = new VisualSet();
    VisualRow visualRow = visualSet.add(domain, error);
    JFreeChart jFreeChart = ListPlot.of(visualSet, true);
    jFreeChart.setBackgroundPaint(Color.WHITE);
    ChartUtils.saveChartAsPNG(HomeDirectory.Pictures(ChebyshevApprox.class.getSimpleName() + ".png"), jFreeChart, 600, 400);
  }
}
