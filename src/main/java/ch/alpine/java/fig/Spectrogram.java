// code by jph
package ch.alpine.java.fig;

import java.util.function.Function;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;

import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.Unprotect;
import ch.alpine.tensor.alg.OrderedQ;
import ch.alpine.tensor.alg.Rescale;
import ch.alpine.tensor.api.ScalarUnaryOperator;
import ch.alpine.tensor.ext.Integers;
import ch.alpine.tensor.fft.SpectrogramArray;
import ch.alpine.tensor.img.ColorDataGradients;
import ch.alpine.tensor.io.ImageFormat;
import ch.alpine.tensor.qty.CompatibleUnitQ;
import ch.alpine.tensor.qty.Unit;
import ch.alpine.tensor.sca.Abs;
import ch.alpine.tensor.sca.win.DirichletWindow;
import ch.alpine.tensor.sca.win.HannWindow;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Spectrogram.html">Spectrogram</a> */
public enum Spectrogram {
  ;
  /** Remark: the unit of the signal is in the color not the axis
   * 
   * @param visualSet with single {@link VisualRow} containing domain and signal
   * @param window for instance {@link HannWindow#FUNCTION}
   * @param function for instance {@link ColorDataGradients#VISIBLESPECTRUM}
   * @return */
  public static JFreeChart of(VisualSet visualSet, ScalarUnaryOperator window, Function<Scalar, ? extends Tensor> function) {
    Integers.requireEquals(visualSet.visualRows().size(), 1);
    VisualRow visualRow = visualSet.getVisualRow(0);
    String string = visualRow.getLabelString();
    if (!string.isBlank())
      System.err.println("spectrogram drops row label");
    // TODO if legend test is defined, indicate that it will not be shown
    Tensor domain = OrderedQ.require(visualRow.points().get(Tensor.ALL, 0));
    Tensor signal = visualRow.points().get(Tensor.ALL, 1);
    Unit unit = visualSet.getAxisY().getUnit();
    Scalar yhi = domain.Get(2).subtract(domain.Get(0)).reciprocal();
    if (!CompatibleUnitQ.SI().with(unit).test(yhi))
      visualSet.getAxisY().setUnit(visualSet.getAxisX().getUnit().negate());
    JFreeChart jFreeChart = new JFreeChart( //
        visualSet.getPlotLabel(), //
        JFreeChart.DEFAULT_TITLE_FONT, //
        new BufferedImagePlot(StaticHelper.create( //
            ImageFormat.of(Rescale.of(half_abs(signal, window)).map(function)), //
            visualSet, domain, yhi)),
        false);
    ChartFactory.getChartTheme().apply(jFreeChart);
    return jFreeChart;
  }

  // TODO use function in tensor library
  private static Tensor half_abs(Tensor vector, ScalarUnaryOperator window) {
    Tensor tensor = SpectrogramArray.of(vector, window);
    int half = Unprotect.dimension1Hint(tensor) / 2;
    return Tensors.vector(i -> tensor.get(Tensor.ALL, half - i - 1).map(Abs.FUNCTION), half);
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
    // TODO use sunset_reversed
    return of(visualSet, window, ColorDataGradients.VISIBLESPECTRUM);
  }

  /** @param visualSet with single {@link VisualRow} containing domain and signal
   * @return spectrogram chart of signal specified in given visual set generated using
   * {@link DirichletWindow} and {@link ColorDataGradients#VISIBLESPECTRUM} */
  public static JFreeChart of(VisualSet visualSet) {
    return of(visualSet, DirichletWindow.FUNCTION);
  }
}
