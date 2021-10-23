// code by gjoel, jph
package ch.alpine.java.fig;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.XYSeriesCollection;

/** Hint:
 * to render list plot in a custom graphics use
 * jFreeChart.draw(graphics2d, rectangle2D)
 * 
 * to export to a graphics file use
 * ChartUtils.saveChartAsPNG(file, jFreeChart, width, height);
 * 
 * to embed figure as separate panel in gui use {@link ChartPanel}.
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/ListPlot.html">ListPlot</a> */
public enum ListPlot {
  ;
  /** @param visualSet
   * @param joined for lines between coordinates, otherwise scattered points
   * @return */
  public static JFreeChart of(VisualSet visualSet, boolean joined) {
    XYSeriesCollection xySeriesCollection = DatasetFactory.xySeriesCollection(visualSet);
    JFreeChart jFreeChart = joined //
        ? ChartFactory.createXYLineChart( //
            visualSet.getPlotLabel(), //
            visualSet.getAxisX().getAxisLabel(), //
            visualSet.getAxisY().getAxisLabel(), //
            xySeriesCollection, PlotOrientation.VERTICAL, //
            visualSet.hasLegend(), // legend
            false, // tooltips
            false) // urls
        : ChartFactory.createScatterPlot( //
            visualSet.getPlotLabel(), //
            visualSet.getAxisX().getAxisLabel(), //
            visualSet.getAxisY().getAxisLabel(), //
            xySeriesCollection, PlotOrientation.VERTICAL, //
            visualSet.hasLegend(), // legend
            false, // tooltips
            false); // urls
    XYPlot xyPlot = jFreeChart.getXYPlot();
    System.out.println(xyPlot.getDomainAxis().getTickLabelFont());
    XYItemRenderer xyItemRenderer = xyPlot.getRenderer();
    int limit = xySeriesCollection.getSeriesCount();
    for (int index = 0; index < limit; ++index) {
      VisualRow visualRow = visualSet.getVisualRow(index);
      xyItemRenderer.setSeriesPaint(index, visualRow.getColor());
      xyItemRenderer.setSeriesStroke(index, visualRow.getStroke());
    }
    { // Mathematica does not include zero in the y-axes by default
      // whereas jfreechart does so.
      // the code below emulates the behavior of Mathematica
      ValueAxis valueAxis = jFreeChart.getXYPlot().getRangeAxis();
      if (valueAxis instanceof NumberAxis) {
        NumberAxis numberAxis = (NumberAxis) valueAxis;
        numberAxis.setAutoRangeIncludesZero(false);
      }
    }
    return jFreeChart;
  }

  /** Mathematica's default is to draw data points as separate dots,
   * i.e. "Joined->False".
   * 
   * @param visualSet
   * @return */
  public static JFreeChart of(VisualSet visualSet) {
    return of(visualSet, false);
  }
}
