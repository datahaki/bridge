// code by jph
package demo.tensor.sca;

import java.awt.Dimension;
import java.io.IOException;

import ch.alpine.bridge.fig.ListLinePlot;
import ch.alpine.bridge.fig.Show;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.alg.Subdivide;
import ch.alpine.tensor.api.ScalarUnaryOperator;
import ch.alpine.tensor.ext.HomeDirectory;
import ch.alpine.tensor.io.Pretty;
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
    Tensor domain = Subdivide.of(-1, 1, 100);
    Show show = new Show();
    for (ChebyshevNodes chebyshevNodes : ChebyshevNodes.values()) {
      Tensor coeffs = LinearSolve.of(chebyshevNodes.matrix(n), chebyshevNodes.of(n).map(suo));
      System.out.println(Pretty.of(coeffs.map(Round._3)));
      Tensor error = domain.map(ClenshawChebyshev.of(coeffs)).subtract(domain.map(suo));
      show.add(ListLinePlot.of(domain, error));
    }
    show.export(HomeDirectory.Pictures(ChebyshevApprox.class.getSimpleName() + ".png"), new Dimension(600, 400));
  }
}
