// code by jph
package ch.alpine.java.fig;

import java.awt.image.BufferedImage;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.Plot;

import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.alg.Last;
import ch.alpine.tensor.api.ScalarUnaryOperator;
import ch.alpine.tensor.ext.Integers;
import ch.alpine.tensor.qty.UnitConvert;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/ArrayPlot.html">ArrayPlot</a> */
public enum ArrayPlot {
  ;
  public static JFreeChart of(VisualSet visualSet, BufferedImage bufferedImage) {
    Integers.requireEquals(visualSet.visualRows().size(), 1);
    VisualRow visualRow = visualSet.getVisualRow(0);
    // ---
    VisualSet _visualSet = new VisualSet();
    ScalarUnaryOperator suo = UnitConvert.SI().to(visualSet.getAxisX().getUnit());
    Tensor points = visualRow.points();
    _visualSet.add( //
        Tensors.of(points.Get(0, 0), Last.of(points).Get(0)).map(suo), //
        Tensors.vectorInt(0, bufferedImage.getHeight()));
    Plot plot = new BufferedImagePlot(bufferedImage, null); // FIXME
    JFreeChart jFreeChart = new JFreeChart(visualSet.getPlotLabel(), JFreeChart.DEFAULT_TITLE_FONT, plot, true);
    ChartFactory.getChartTheme().apply(jFreeChart);
    return jFreeChart;
  }
}
