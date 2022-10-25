// code by jph
package demo.tensor;

import java.awt.Dimension;
import java.io.IOException;

import ch.alpine.bridge.fig.Plot;
import ch.alpine.bridge.fig.Show;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.api.ScalarUnaryOperator;
import ch.alpine.tensor.ext.HomeDirectory;
import ch.alpine.tensor.sca.Clip;
import ch.alpine.tensor.sca.Clips;
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
    Clip clip = Clips.absoluteOne();
    ChebyshevNodes chebyshevNodes = ChebyshevNodes._1;
    ScalarUnaryOperator suo1 = ChebyshevInterpolation.of(suo0, chebyshevNodes, n);
    ScalarUnaryOperator suo2 = ChebyshevInterpolation.alt(suo0, chebyshevNodes, n);
    Tensor knots = chebyshevNodes.of(n);
    InterpolatingPolynomial ip = InterpolatingPolynomial.of(knots);
    ScalarUnaryOperator suo3 = ip.scalarUnaryOperator(knots.map(suo0));
    {
      Show show = new Show();
      show.add(Plot.of(suo0, clip)).setLabel("f");
      show.add(Plot.of(suo1, clip)).setLabel("interp");
      show.add(Plot.of(suo2, clip)).setLabel("alt");
      show.add(Plot.of(suo3, clip)).setLabel("inpol");
      show.export(HomeDirectory.Pictures(ChebyshevInterpDemo.class.getSimpleName() + ".png"), new Dimension(600, 400));
    }
    {
      Show show = new Show();
      show.add(Plot.of(s -> suo1.apply(s).subtract(suo2.apply(s)), clip));
      show.export(HomeDirectory.Pictures(ChebyshevInterpDemo.class.getSimpleName() + "_error.png"), new Dimension(600, 400));
    }
  }
}
