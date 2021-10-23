// code by jph
package ch.alpine.java.fig;

import java.util.function.Function;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;

import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.api.ScalarUnaryOperator;
import ch.alpine.tensor.ext.Integers;
import ch.alpine.tensor.img.ColorDataGradients;
import ch.alpine.tensor.io.ImageFormat;
import ch.alpine.tensor.sca.win.DirichletWindow;
import ch.alpine.tensor.sca.win.HannWindow;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Spectrogram.html">Spectrogram</a> */
public enum Spectrogram {
  ;
  /** @param visualSet with single {@link VisualRow} containing domain and signal
   * @param window for instance {@link HannWindow#FUNCTION}
   * @param function for instance {@link ColorDataGradients#VISIBLESPECTRUM}
   * @return */
  public static JFreeChart of(VisualSet visualSet, ScalarUnaryOperator window, Function<Scalar, ? extends Tensor> function) {
    Integers.requireEquals(visualSet.visualRows().size(), 1);
    VisualRow visualRow = visualSet.getVisualRow(0);
    // TODO if legend test is defined, indicate that it will not be shown
    Tensor domain = visualRow.points().get(Tensor.ALL, 0);
    Tensor signal = visualRow.points().get(Tensor.ALL, 1);
    Scalar yhi = domain.Get(2).subtract(domain.Get(0)).reciprocal();
    JFreeChart jFreeChart = new JFreeChart( //
        visualSet.getPlotLabel(), //
        JFreeChart.DEFAULT_TITLE_FONT, //
        new BufferedImagePlot(StaticHelper.create( //
            ImageFormat.of(ch.alpine.tensor.fft.Spectrogram.of(signal, window, function)), //
            visualSet, domain, yhi)),
        false);
    ChartFactory.getChartTheme().apply(jFreeChart);
    return jFreeChart;
  }

  /** Example:
   * <pre>
   * Spectrogram.of(vector, HannWindow.FUNCTION);
   * </pre>
   * 
   * @param visualSet with single {@link VisualRow} containing domain and signal
   * @param window for instance {@link HannWindow#FUNCTION}
   * @return spectrogram chart of signal specified in given visual set generated using
   * given window function and {@link ColorDataGradients#VISIBLESPECTRUM} */
  public static JFreeChart of(VisualSet visualSet, ScalarUnaryOperator window) {
    return of(visualSet, window, ColorDataGradients.VISIBLESPECTRUM);
  }

  /** @param visualSet with single {@link VisualRow} containing domain and signal
   * @return spectrogram chart of signal specified in given visual set generated using
   * {@link DirichletWindow} and {@link ColorDataGradients#VISIBLESPECTRUM} */
  public static JFreeChart of(VisualSet visualSet) {
    return of(visualSet, DirichletWindow.FUNCTION);
  }
}
