// code by jph
package showcase.fig;

import java.awt.BasicStroke;

import ch.alpine.bridge.fig.Plot;
import ch.alpine.bridge.fig.Show;
import ch.alpine.bridge.fig.Showable;
import ch.alpine.bridge.lang.ShowProvider;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.api.ScalarUnaryOperator;
import ch.alpine.tensor.itp.LinearInterpolation;
import ch.alpine.tensor.sca.Clip;
import ch.alpine.tensor.sca.Clips;
import ch.alpine.tensor.sca.ply.ChebyshevNodes;
import ch.alpine.tensor.sca.ply.InterpolatingPolynomial;
import ch.alpine.tensor.sca.ply.Polynomial;

class InterpolatingPolynomialDemo implements ShowProvider {
  @Override
  public Show getShow() {
    Polynomial f = Polynomial.of(Tensors.vector(3, 2, .3, -1));
    Show show = new Show();
    Clip clip = Clips.interval(0.3, 0.8);
    Showable showable = show.add(Plot.of(f, clip));
    showable.setStroke(new BasicStroke(10));
    for (int d = 1; d < 10; ++d) {
      Tensor init = ChebyshevNodes._1.of(d);
      Tensor knots = init.map(Clips.absoluteOne()::rescale);
      knots = knots.map(LinearInterpolation.of(clip));
      InterpolatingPolynomial interpolatingPolynomial = InterpolatingPolynomial.of(knots);
      ScalarUnaryOperator suo = interpolatingPolynomial.scalarUnaryOperator(knots.map(f));
      Showable showable2 = show.add(Plot.of(suo, clip));
      showable2.setLabel("deg " + d);
    }
    return show;
  }

  static void main() {
    new InterpolatingPolynomialDemo().run();
  }
}
