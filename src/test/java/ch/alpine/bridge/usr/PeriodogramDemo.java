// code by jph
package ch.alpine.bridge.usr;

import ch.alpine.bridge.fig.Periodogram;
import ch.alpine.bridge.fig.Show;
import ch.alpine.bridge.fig.Showable;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.alg.Range;
import ch.alpine.tensor.alg.Subdivide;
import ch.alpine.tensor.alg.Transpose;
import ch.alpine.tensor.api.ScalarUnaryOperator;
import ch.alpine.tensor.num.Pi;
import ch.alpine.tensor.pdf.RandomVariate;
import ch.alpine.tensor.pdf.c.UniformDistribution;
import ch.alpine.tensor.red.Times;
import ch.alpine.tensor.sca.tri.Sin;

public enum PeriodogramDemo {
  ;
  public static Showable create() {
    Scalar f0 = Pi.TWO.multiply(RealScalar.of(697));
    Scalar f1 = Pi.TWO.multiply(RealScalar.of(1209));
    ScalarUnaryOperator suo = t -> Sin.FUNCTION.apply(f0.multiply(t)).add(Sin.FUNCTION.apply(f1.multiply(t)));
    Tensor domain = Subdivide.of(0.0, 0.3, 2400);
    Tensor signal = domain.map(suo);
    Tensor points = Transpose.of(Tensors.of(domain, signal));
    return Periodogram.of(points);
  }

  private static Scalar _f(Scalar n) {
    Scalar x1 = Sin.FUNCTION.apply(Times.of(RealScalar.of(0.2), n, Pi.VALUE));
    Scalar x2 = Sin.FUNCTION.apply(Times.of(RealScalar.of(0.5), n, Pi.VALUE));
    return x1.add(x1).add(x2);
  }

  public static Show create2() {
    int n = 128;
    Tensor noised = RandomVariate.of(UniformDistribution.of(-1, 1), n);
    Tensor domain = Range.of(0, n);
    Tensor signal = Tensors.vector(i -> _f(RealScalar.of(i)), n).add(noised);
    Show show = new Show();
    show.setPlotLabel(Periodogram.class.getSimpleName());
    Tensor points = Transpose.of(Tensors.of(domain, signal));
    show.add(Periodogram.of(points));
    return show;
  }
}
