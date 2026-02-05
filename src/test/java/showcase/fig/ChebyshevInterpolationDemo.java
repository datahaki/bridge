package showcase.fig;

import java.awt.BasicStroke;

import ch.alpine.bridge.fig.Plot;
import ch.alpine.bridge.fig.Show;
import ch.alpine.bridge.fig.Showable;
import ch.alpine.bridge.lang.ShowProvider;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.api.ScalarUnaryOperator;
import ch.alpine.tensor.sca.Clips;
import ch.alpine.tensor.sca.ply.ChebyshevInterpolation;
import ch.alpine.tensor.sca.ply.Polynomial;

class ChebyshevInterpolationDemo implements ShowProvider {
  @Override
  public Show getShow() {
    Polynomial f = Polynomial.of(Tensors.vector(3, .2, .3, -1));
    Show show = new Show();
    show.setPlotLabel("ChebyshevInterpolation");
    Showable showable = show.add(Plot.of(f, Clips.absoluteOne()));
    showable.setStroke(new BasicStroke(10));
    for (int d = 1; d < 10; ++d) {
      ScalarUnaryOperator suo = ChebyshevInterpolation.of(f, d);
      Showable showable2 = show.add(Plot.of(suo, Clips.absoluteOne()));
      showable2.setLabel("deg " + d);
    }
    return show;
  }

  static void main() {
    new ChebyshevInterpolationDemo().run();
  }
}
