// code by jph
package ch.alpine.java.fig;

import java.awt.image.BufferedImage;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;

import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.api.ScalarUnaryOperator;
import ch.alpine.tensor.ext.Integers;
import ch.alpine.tensor.img.ColorDataGradients;
import ch.alpine.tensor.io.ImageFormat;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Spectrogram.html">Spectrogram</a> */
public enum Spectrogram {
  ;
  /** @param visualSet with single {@link VisualRow} containing domain and signal
   * @param window
   * @return */
  public static JFreeChart of(VisualSet visualSet, ScalarUnaryOperator window) {
    Integers.requireEquals(visualSet.visualRows().size(), 1);
    VisualRow visualRow = visualSet.getVisualRow(0);
    // TODO if legend test is defined, indicate that it will not be shown
    Tensor domain = visualRow.points().get(Tensor.ALL, 0);
    Tensor signal = visualRow.points().get(Tensor.ALL, 1);
    BufferedImage bufferedImage = ImageFormat.of(ch.alpine.tensor.fft.Spectrogram.of(signal, window, ColorDataGradients.VISIBLESPECTRUM));
    Scalar yhi = domain.Get(2).subtract(domain.Get(0)).reciprocal();
    JFreeChart jFreeChart = new JFreeChart( //
        visualSet.getPlotLabel(), //
        JFreeChart.DEFAULT_TITLE_FONT, //
        new BufferedImagePlot(bufferedImage, StaticHelper.create(visualSet, domain, yhi)), false);
    ChartFactory.getChartTheme().apply(jFreeChart);
    return jFreeChart;
  }
}
