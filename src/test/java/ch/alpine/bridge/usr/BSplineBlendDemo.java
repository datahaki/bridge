// code by jph
package ch.alpine.bridge.usr;

import ch.alpine.bridge.fig.Plot;
import ch.alpine.bridge.fig.Show;
import ch.alpine.bridge.fig.ShowDialog;
import ch.alpine.bridge.fig.Showable;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.alg.UnitVector;
import ch.alpine.tensor.itp.BSplineInterpolation;
import ch.alpine.tensor.itp.Interpolation;
import ch.alpine.tensor.sca.Clips;

/* package */ enum BSplineBlendDemo {
  ;
  public static void main(String[] args) {
    int n = 2;
    Tensor tensor = UnitVector.of(2, 1);
    Show show = new Show();
    for (int degree = 0; degree < 4; ++degree) {
      Interpolation interpolation = BSplineInterpolation.of(degree, tensor);
      Showable showable = show.add(Plot.of(interpolation::At, Clips.positive(n - 1)));
      showable.setLabel("degree " + degree);
    }
    ShowDialog.of(show);
  }
}
