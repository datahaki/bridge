// code by jph
package ch.alpine.java.fig;

import java.awt.Color;
import java.io.File;
import java.io.IOException;

import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;

import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.alg.Subdivide;
import ch.alpine.tensor.api.ScalarUnaryOperator;
import ch.alpine.tensor.ext.HomeDirectory;
import ch.alpine.tensor.num.Pi;
import ch.alpine.tensor.red.Times;
import ch.alpine.tensor.sca.tri.Sin;

public enum PeriodogramDemo {
  ;
  public static JFreeChart create() {
    Scalar f0 = Pi.TWO.multiply(RealScalar.of(697));
    Scalar f1 = Pi.TWO.multiply(RealScalar.of(1209));
    ScalarUnaryOperator suo = new ScalarUnaryOperator() {
      @Override
      public Scalar apply(Scalar t) {
        return Sin.FUNCTION.apply(f0.multiply(t)).add(Sin.FUNCTION.apply(f1.multiply(t)));
      }
    };
    Tensor domain = Subdivide.of(0.0, 0.3, 2400);
    Tensor signal = domain.map(suo);
    VisualSet visualSet = new VisualSet();
    visualSet.add(domain, signal);
    return Periodogram.of(visualSet);
  }

  private static Scalar _f(Scalar n) {
    Scalar x1 = Sin.FUNCTION.apply(Times.of(RealScalar.of(0.2), n, Pi.VALUE));
    Scalar x2 = Sin.FUNCTION.apply(Times.of(RealScalar.of(0.5), n, Pi.VALUE));
    return x1.add(x1).add(x2);
  }

  public static JFreeChart create2() {
    int n = 128;
    Tensor domain = Subdivide.of(0.0, 0.3, n - 1);
    Tensor signal = Tensors.vector(i -> _f(RealScalar.of(i)), n);
    VisualSet visualSet = new VisualSet();
    visualSet.add(domain, signal);
    return Periodogram.of(visualSet);
  }

  public static void main(String[] args) throws IOException {
    JFreeChart jFreeChart = PeriodogramDemo.create2();
    File file = HomeDirectory.Pictures("periodogram.png");
    jFreeChart.setBackgroundPaint(Color.WHITE);
    ChartUtils.saveChartAsPNG(file, jFreeChart, 500, 300);
  }
}
