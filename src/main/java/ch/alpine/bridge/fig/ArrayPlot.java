// code by jph
package ch.alpine.bridge.fig;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;

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
}
