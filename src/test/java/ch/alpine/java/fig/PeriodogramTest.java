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
import ch.alpine.tensor.alg.Subdivide;
import ch.alpine.tensor.api.ScalarUnaryOperator;
import ch.alpine.tensor.ext.HomeDirectory;
import ch.alpine.tensor.num.Pi;
import ch.alpine.tensor.sca.Sin;
import junit.framework.TestCase;

public class PeriodogramTest extends TestCase {
  public void testSimple() throws IOException {
    Scalar f0 = Pi.TWO.multiply(RealScalar.of(697));
    Scalar f1 = Pi.TWO.multiply(RealScalar.of(1209));
    ScalarUnaryOperator suo = new ScalarUnaryOperator() {
      @Override
      public Scalar apply(Scalar t) {
        return Sin.FUNCTION.apply(f0.multiply(t)).add(Sin.FUNCTION.apply(f1.multiply(t)));
      }
    };
    Tensor times = Subdivide.of(0.0, 0.3, 2400);
    Tensor signal = times.map(suo);
    VisualSet visualSet = new VisualSet();
    visualSet.add(times, signal);
    JFreeChart jFreeChart = Periodogram.of(visualSet);
    File file = HomeDirectory.file("periodogram.png");
    jFreeChart.setBackgroundPaint(Color.WHITE);
    ChartUtils.saveChartAsPNG(file, jFreeChart, 500, 300);
  }
}
