// code by jph
package demo.tensor;

import ch.alpine.bridge.fig.Plot;
import ch.alpine.bridge.fig.Show;
import ch.alpine.bridge.fig.ShowDialog;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.api.ScalarUnaryOperator;
import ch.alpine.tensor.sca.Clip;
import ch.alpine.tensor.sca.Clips;
import ch.alpine.tensor.sca.ply.ChebyshevInterpolation;
import ch.alpine.tensor.sca.ply.ChebyshevNodes;
import ch.alpine.tensor.sca.ply.InterpolatingPolynomial;
import ch.alpine.tensor.sca.tri.Sin;

public enum ChebyshevInterpDemo {
  ;
  public static void main(String[] args) {
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
    Show show1 = new Show();
    show1.setPlotLabel("Functions");
    show1.add(Plot.of(suo0, clip)).setLabel("f");
    show1.add(Plot.of(suo1, clip)).setLabel("interp");
    show1.add(Plot.of(suo2, clip)).setLabel("alt");
    show1.add(Plot.of(suo3, clip)).setLabel("inpol");
    Show show2 = new Show();
    show2.setPlotLabel("Error");
    show2.add(Plot.of(s -> suo1.apply(s).subtract(suo2.apply(s)), clip));
    ShowDialog.of(show1, show2);
  }
}
