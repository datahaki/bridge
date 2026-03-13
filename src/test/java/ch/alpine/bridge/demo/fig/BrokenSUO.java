// code by jph
package ch.alpine.bridge.demo.fig;

import ch.alpine.bridge.fig.Show;
import ch.alpine.bridge.fig.plt.Plot;
import ch.alpine.bridge.pro.ShowProvider;
import ch.alpine.tensor.DoubleScalar;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.api.ScalarUnaryOperator;
import ch.alpine.tensor.sca.Clips;
import ch.alpine.tensor.sca.tri.Sin;

class BrokenSUO implements ScalarUnaryOperator, ShowProvider {
  @Override
  public Scalar apply(Scalar t) {
    // if (Clips.interval(1, 1.1).isInside(t))
    // throw new RuntimeException();
    if (Clips.interval(2, 2.8).isInside(t))
      return DoubleScalar.INDETERMINATE;
    if (Clips.interval(4, 4.8).isInside(t))
      return DoubleScalar.POSITIVE_INFINITY;
    if (Clips.interval(6, 6.8).isInside(t))
      return DoubleScalar.NEGATIVE_INFINITY;
    return Sin.FUNCTION.apply(t.multiply(RealScalar.of(10)));
  }

  @Override
  public Show getShow() {
    Show show = new Show();
    show.add(Plot.of(this, Clips.positive(10)));
    return show;
  }

  static void main() {
    new BrokenSUO().runStandalone();
  }
}
