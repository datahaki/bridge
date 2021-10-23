// code by jph
package ch.alpine.java.fig;

import java.awt.image.BufferedImage;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;

import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.ext.Integers;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/ArrayPlot.html">ArrayPlot</a> */
public enum ArrayPlot {
  ;
  public static JFreeChart of(VisualSet visualSet, BufferedImage bufferedImage) {
    Integers.requireEquals(visualSet.visualRows().size(), 1);
    VisualRow visualRow = visualSet.getVisualRow(0);
    Tensor domain = visualRow.points().get(Tensor.ALL, 0);
    JFreeChart jFreeChart = new JFreeChart( //
        visualSet.getPlotLabel(), //
        JFreeChart.DEFAULT_TITLE_FONT, //
        new BufferedImagePlot(bufferedImage, StaticHelper.create(visualSet, domain, RealScalar.of(bufferedImage.getHeight()))), //
        true);
    ChartFactory.getChartTheme().apply(jFreeChart);
    return jFreeChart;
  }
}
