// code by jph
package ch.alpine.java.fig;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/ArrayPlot.html">ArrayPlot</a> */
public enum ArrayPlot {
  ;
  public static JFreeChart of(VisualImage visualArray) {
    JFreeChart jFreeChart = new JFreeChart( //
        visualArray.getPlotLabel(), //
        JFreeChart.DEFAULT_TITLE_FONT, //
        new BufferedImagePlot(visualArray), //
        false); // no legend
    ChartFactory.getChartTheme().apply(jFreeChart);
    return jFreeChart;
  }
}
