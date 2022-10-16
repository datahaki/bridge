// code by jph
package ch.alpine.bridge.usr;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;

import ch.alpine.bridge.fig.Showable;
import ch.alpine.bridge.fig.Periodogram;
import ch.alpine.bridge.fig.Show;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.alg.Range;
import ch.alpine.tensor.alg.Subdivide;
import ch.alpine.tensor.api.ScalarUnaryOperator;
import ch.alpine.tensor.ext.HomeDirectory;
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
    Show visualSet = new Show();
    visualSet.add(domain, signal);
    return Periodogram.of(visualSet);
  }

  private static Scalar _f(Scalar n) {
    Scalar x1 = Sin.FUNCTION.apply(Times.of(RealScalar.of(0.2), n, Pi.VALUE));
    Scalar x2 = Sin.FUNCTION.apply(Times.of(RealScalar.of(0.5), n, Pi.VALUE));
    return x1.add(x1).add(x2);
  }

  public static Showable create2() {
    int n = 128;
    Tensor noised = RandomVariate.of(UniformDistribution.of(-1, 1), n);
    Tensor domain = Range.of(0, n);
    Tensor signal = Tensors.vector(i -> _f(RealScalar.of(i)), n).add(noised);
    Show visualSet = new Show();
    visualSet.setPlotLabel(Periodogram.class.getSimpleName());
    visualSet.add(domain, signal);
    return Periodogram.of(visualSet);
  }

  public static void main(String[] args) throws IOException {
    Showable jFreeChart = PeriodogramDemo.create2();
    File file = HomeDirectory.Pictures(Periodogram.class.getSimpleName() + ".png");
    Show.export(file, jFreeChart, //
        new Dimension(DemoHelper.DEMO_W, DemoHelper.DEMO_H));
  }
}
