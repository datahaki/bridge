// code by jph
package showcase.fig;

import ch.alpine.bridge.fig.ListPlot;
import ch.alpine.bridge.fig.Plot;
import ch.alpine.bridge.fig.Show;
import ch.alpine.bridge.fig.Showable;
import ch.alpine.bridge.lang.ShowProvider;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.itp.BSplineInterpolation;
import ch.alpine.tensor.itp.Interpolation;
import ch.alpine.tensor.itp.LanczosInterpolation;
import ch.alpine.tensor.pdf.RandomVariate;
import ch.alpine.tensor.pdf.c.UniformDistribution;
import ch.alpine.tensor.sca.Clip;
import ch.alpine.tensor.sca.Clips;

class BSplineInterpDemo implements ShowProvider {
  @Override
  public Show getShow() {
    int n = 10;
    Tensor tensor = RandomVariate.of(UniformDistribution.unit(20), n);
    Show show = new Show();
    show.setPlotLabel("BSplineInterpolation");
    Tensor points = Tensors.vector(i -> Tensors.of(RealScalar.of(i), tensor.Get(i)), n);
    {
      Showable showable = show.add(ListPlot.of(points));
      showable.setLabel("points");
    }
    Clip domain = Clips.positive(n - 1);
    for (int degree = 0; degree < 3; ++degree) {
      Interpolation interpolation = BSplineInterpolation.of(degree, tensor);
      Showable showable = show.add(Plot.of(interpolation::At, domain));
      showable.setLabel("degree " + degree);
    }
    {
      Interpolation interpolation = LanczosInterpolation.of(tensor);
      Showable showable = show.add(Plot.of(interpolation::At, domain));
      showable.setLabel("Lanczos");
    }
    return show;
  }

  static void main() {
    new BSplineInterpDemo().run();
  }
}
