// code by jph
package demo.tensor.sca;

import java.awt.Dimension;
import java.io.IOException;

import ch.alpine.bridge.fig.ListLinePlot;
import ch.alpine.bridge.fig.Plot;
import ch.alpine.bridge.fig.Show;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.alg.Subdivide;
import ch.alpine.tensor.alg.UnitVector;
import ch.alpine.tensor.api.ScalarUnaryOperator;
import ch.alpine.tensor.ext.HomeDirectory;
import ch.alpine.tensor.sca.Clips;
import ch.alpine.tensor.sca.ply.Chebyshev;
import ch.alpine.tensor.sca.ply.ClenshawChebyshev;

public enum ChebyshevDemo {
  ;
  public static void main(String[] args) throws IOException {
    int max = 6;
    {
      Tensor domain = Subdivide.of(-1., 1., 30);
      Show show = new Show();
      for (int d = 0; d < max; ++d) {
        ScalarUnaryOperator suo = ClenshawChebyshev.of(UnitVector.of(d + 1, d));
        ScalarUnaryOperator su2 = Chebyshev.T.of(d);
        show.add(ListLinePlot.of(domain, domain.map(suo).subtract(domain.map(su2))));
      }
      show.export(HomeDirectory.Pictures(ChebyshevDemo.class.getSimpleName() + ".png"), new Dimension(600, 400));
    }
    {
      Show show = new Show();
      for (int d = 0; d < max; ++d) {
        ScalarUnaryOperator suo = Chebyshev.T.of(d);
        show.add(Plot.of(suo, Clips.absolute(1)));
      }
      show.setPlotLabel("Chebyshev Polynomials");
      show.export(HomeDirectory.Pictures(Chebyshev.class.getSimpleName() + "T.png"), new Dimension(600, 400));
    }
    {
      Show show = new Show();
      for (int d = 0; d < max; ++d) {
        ScalarUnaryOperator suo = Chebyshev.U.of(d);
        show.add(Plot.of(suo, Clips.absolute(1)));
      }
      show.setPlotLabel("Chebyshev Polynomials");
      show.export(HomeDirectory.Pictures(Chebyshev.class.getSimpleName() + "U.png"), new Dimension(600, 400));
    }
  }
}
