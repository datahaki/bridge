// code by jph
package ch.alpine.java.fig;

import java.awt.image.BufferedImage;
import java.util.function.Function;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.Plot;

import ch.alpine.tensor.RationalScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.alg.Last;
import ch.alpine.tensor.api.ScalarUnaryOperator;
import ch.alpine.tensor.ext.Integers;
import ch.alpine.tensor.io.ImageFormat;
import ch.alpine.tensor.qty.UnitConvert;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Spectrogram.html">Spectrogram</a> */
public enum Spectrogram {
  ;
  /** @param visualSet with single {@link VisualRow} containing domain and signal
   * @param window
   * @param function
   * @return */
  public static JFreeChart of(VisualSet visualSet, ScalarUnaryOperator window, Function<Scalar, ? extends Tensor> function) {
    Integers.requireEquals(visualSet.visualRows().size(), 1);
    VisualRow visualRow = visualSet.getVisualRow(0);
    Tensor domain = visualRow.points().get(Tensor.ALL, 0);
    Tensor signal = visualRow.points().get(Tensor.ALL, 1);
    BufferedImage bufferedImage = ImageFormat.of(ch.alpine.tensor.fft.Spectrogram.of(signal, window, function));
    Scalar dt = domain.Get(1).subtract(domain.Get(0));
    Scalar yhi = dt.reciprocal().multiply(RationalScalar.HALF);
    // ---
    VisualSet _visualSet = new VisualSet();
    ScalarUnaryOperator suo = UnitConvert.SI().to(visualSet.getAxisX().getUnit());
    Tensor points = visualRow.points();
    _visualSet.add( //
        Tensors.of(points.Get(0, 0), Last.of(points).Get(0)).map(suo), //
        Tensors.of(yhi.zero(), yhi));
    Plot plot = new BufferedImagePlot(bufferedImage, _visualSet);
    JFreeChart jFreeChart = new JFreeChart(visualSet.getPlotLabel(), JFreeChart.DEFAULT_TITLE_FONT, plot, true);
    ChartFactory.getChartTheme().apply(jFreeChart);
    return jFreeChart;
  }
}
