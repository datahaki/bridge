// code by jph
package ch.ethz.idsc.tensor.fig;

import java.awt.Color;
import java.io.File;
import java.io.IOException;

import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;

import ch.ethz.idsc.tensor.RealScalar;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.Subdivide;
import ch.ethz.idsc.tensor.api.ScalarUnaryOperator;
import ch.ethz.idsc.tensor.ext.HomeDirectory;
import ch.ethz.idsc.tensor.num.Pi;
import ch.ethz.idsc.tensor.sca.Sin;
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
