// code by jph
package ch.alpine.bridge.fig;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;

import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.img.ColorDataGradient;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/ArrayPlot.html">ArrayPlot</a> */
public enum ArrayPlot {
  ;
  /** @param visualImage
   * @return */
  public static JFreeChart of(VisualImage visualImage) {
    JFreeChart jFreeChart = new JFreeChart( //
        visualImage.getPlotLabel(), //
        JFreeChart.DEFAULT_TITLE_FONT, //
        new BufferedImagePlot(visualImage), //
        false); // no legend
    ChartFactory.getChartTheme().apply(jFreeChart);
    return jFreeChart;
  }

  /** function exists to emulate
   * ArrayPlot[matrix] in Mathematica
   * 
   * @param matrix
   * @return */
  public static JFreeChart of(Tensor matrix) {
    return of(VisualImage.of(matrix));
  }

  public static JFreeChart of(Tensor matrix, ColorDataGradient colorDataGradient) {
    return of(VisualImage.of(matrix, colorDataGradient));
  }
}
