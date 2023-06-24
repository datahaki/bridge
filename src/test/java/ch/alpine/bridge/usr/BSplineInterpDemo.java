// code by jph
package ch.alpine.bridge.usr;

import ch.alpine.bridge.fig.ListPlot;
import ch.alpine.bridge.fig.Plot;
import ch.alpine.bridge.fig.Show;
import ch.alpine.bridge.fig.ShowDialog;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.itp.BSplineInterpolation;
import ch.alpine.tensor.itp.Interpolation;
import ch.alpine.tensor.pdf.RandomVariate;
import ch.alpine.tensor.pdf.c.UniformDistribution;
import ch.alpine.tensor.sca.Clips;

/* package */ enum BSplineInterpDemo {
  ;
  public static void main(String[] args) {
    int n = 10;
    Tensor tensor = RandomVariate.of(UniformDistribution.unit(), n);
    Show show = new Show();
    Tensor points = Tensors.vector(i -> Tensors.of(RealScalar.of(i), tensor.Get(i)), n);
    show.add(ListPlot.of(points));
    for (int degree = 0; degree < 4; ++degree) {
      Interpolation interpolation = BSplineInterpolation.of(degree, tensor);
      show.add(Plot.of(interpolation::At, Clips.positive(n - 1)));
    }
    ShowDialog.of(show);
  }
}
