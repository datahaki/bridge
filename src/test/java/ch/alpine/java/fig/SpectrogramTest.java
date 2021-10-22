// code by jph
package ch.alpine.java.fig;

import java.awt.Color;
import java.io.IOException;

import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;

import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.alg.Subdivide;
import ch.alpine.tensor.api.ScalarUnaryOperator;
import ch.alpine.tensor.ext.HomeDirectory;
import ch.alpine.tensor.img.ColorDataGradients;
import ch.alpine.tensor.num.Pi;
import ch.alpine.tensor.num.Polynomial;
import ch.alpine.tensor.qty.Quantity;
import ch.alpine.tensor.sca.Cos;
import junit.framework.TestCase;

public class SpectrogramTest extends TestCase {
  public void testSimple() throws IOException {
    ScalarUnaryOperator polynomial = Polynomial.of(Tensors.vector( //
        0, //
        800, //
        2800).multiply(Pi.VALUE));
    Tensor domain = Subdivide.of(RealScalar.of(0.0), RealScalar.of(1.6), (int) (8000 * 1.6));
    // Tensor domain = Subdivide.of(RealScalar.of(0.0), RationalScalar.of(8192, 6000), 8191);
    Tensor chirp = domain.map(polynomial).map(Cos.FUNCTION);
    // plot.setOrientation(PlotOrientation.VERTICAL);
    JFreeChart jFreeChart = Spectrogram.of(chirp, Quantity.of(1.0 / 8000, "s"), ColorDataGradients.VISIBLESPECTRUM);
    jFreeChart.setBackgroundPaint(Color.WHITE);
    // TODO this is more like a demo
    ChartUtils.saveChartAsPNG(HomeDirectory.Pictures("spectrogram.png"), jFreeChart, 1024, 320);
  }
}
