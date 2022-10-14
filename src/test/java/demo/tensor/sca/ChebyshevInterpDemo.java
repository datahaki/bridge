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
import ch.alpine.tensor.api.ScalarUnaryOperator;
import ch.alpine.tensor.ext.HomeDirectory;
import ch.alpine.tensor.sca.ply.ChebyshevInterpolation;
import ch.alpine.tensor.sca.ply.ChebyshevNodes;
import ch.alpine.tensor.sca.ply.InterpolatingPolynomial;
import ch.alpine.tensor.sca.tri.Sin;

public enum ChebyshevInterpDemo {
  ;
  public static void main(String[] args) throws IOException {
    int n = 16;
    ScalarUnaryOperator suo0 = x -> Sin.FUNCTION.apply(x.multiply(x).negate().add(x));
    // suo = Exp.FUNCTION;
    Tensor domain = Subdivide.of(-1, 1, 100);
    ChebyshevNodes chebyshevNodes = ChebyshevNodes._1;
    ScalarUnaryOperator suo1 = ChebyshevInterpolation.of(suo0, chebyshevNodes, n);
    ScalarUnaryOperator suo2 = ChebyshevInterpolation.alt(suo0, chebyshevNodes, n);
    Tensor knots = chebyshevNodes.of(n);
    InterpolatingPolynomial ip = InterpolatingPolynomial.of(knots);
    ScalarUnaryOperator suo3 = ip.scalarUnaryOperator(knots.map(suo0));
    {
      VisualSet visualSet = new VisualSet();
      visualSet.add(domain, domain.map(suo0)).setLabel("f");
      visualSet.add(domain, domain.map(suo1)).setLabel("interp");
      visualSet.add(domain, domain.map(suo2)).setLabel("alt");
      visualSet.add(domain, domain.map(suo3)).setLabel("inpol");
      JFreeChart jFreeChart = ListPlot.of(visualSet, true);
      ChartUtils.saveChartAsPNG(HomeDirectory.Pictures(ChebyshevInterpDemo.class.getSimpleName() + ".png"), jFreeChart, new Dimension(600, 400));
    }
    {
      VisualSet visualSet = new VisualSet();
      Tensor error = domain.map(suo1).subtract(domain.map(suo2));
      visualSet.add(domain, error);
      JFreeChart jFreeChart = ListPlot.of(visualSet, true);
      ChartUtils.saveChartAsPNG(HomeDirectory.Pictures(ChebyshevInterpDemo.class.getSimpleName() + "_error.png"), jFreeChart, new Dimension(600, 400));
    }
  }
}
