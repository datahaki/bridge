// code by jph
package ch.alpine.java.fig;

import java.awt.image.BufferedImage;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;

import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.alg.Last;
import ch.alpine.tensor.api.ScalarUnaryOperator;
import ch.alpine.tensor.ext.Integers;
import ch.alpine.tensor.img.ColorDataGradients;
import ch.alpine.tensor.io.ImageFormat;
import ch.alpine.tensor.opt.nd.Box;
import ch.alpine.tensor.qty.Unit;
import ch.alpine.tensor.qty.UnitConvert;

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
    Tensor domain = visualRow.points().get(Tensor.ALL, 0);
    Tensor signal = visualRow.points().get(Tensor.ALL, 1);
    BufferedImage bufferedImage = ImageFormat.of(ch.alpine.tensor.fft.Spectrogram.of(signal, window, ColorDataGradients.VISIBLESPECTRUM));
    Scalar yhi = domain.Get(2).subtract(domain.Get(0)).reciprocal();
    // ---
    Unit unitX = visualSet.getAxisX().getUnit();
    Unit unitY = visualSet.getAxisY().getUnit();
    ScalarUnaryOperator suoX = UnitConvert.SI().to(unitX);
    ScalarUnaryOperator suoY = UnitConvert.SI().to(unitY);
    Box box = Box.of( //
        Tensors.of(suoX.apply(domain.Get(0)), suoY.apply(yhi.zero())), //
        Tensors.of(suoX.apply(Last.of(domain)), suoY.apply(yhi)));
    VisualArray visualArray = new VisualArray(box, null);
    visualArray.getAxisX().setLabel(visualSet.getAxisX().getLabel());
    visualArray.getAxisY().setLabel(visualSet.getAxisY().getLabel());
    BufferedImagePlot bufferedImagePlot = new BufferedImagePlot(bufferedImage, visualArray);
    JFreeChart jFreeChart = new JFreeChart(visualSet.getPlotLabel(), JFreeChart.DEFAULT_TITLE_FONT, bufferedImagePlot, true);
    ChartFactory.getChartTheme().apply(jFreeChart);
    return jFreeChart;
  }
}
