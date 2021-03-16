// code by gjoel, jph
package ch.ethz.idsc.tensor.fig;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.XYSeriesCollection;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/ListPlot.html">ListPlot</a> */
public enum ListPlot {
  ;
  /** Hint: use
   * jFreeChart.draw(graphics2d, rectangle2D) to render list plot
   * 
   * @param visualSet
   * @param joined for lines between coordinates, otherwise scattered points
   * @return */
  public static JFreeChart of(VisualSet visualSet, boolean joined) {
    XYSeriesCollection xySeriesCollection = DatasetFactory.xySeriesCollection(visualSet);
    JFreeChart jFreeChart = joined //
        ? ChartFactory.createXYLineChart( //
            visualSet.getPlotLabel(), //
            visualSet.getAxesLabelX(), //
            visualSet.getAxesLabelY(), //
            xySeriesCollection, PlotOrientation.VERTICAL, //
            visualSet.hasLegend(), // legend
            false, // tooltips
            false) // urls
        : ChartFactory.createScatterPlot( //
            visualSet.getPlotLabel(), //
            visualSet.getAxesLabelX(), //
            visualSet.getAxesLabelY(), //
            xySeriesCollection, PlotOrientation.VERTICAL, //
            visualSet.hasLegend(), // legend
            false, // tooltips
            false); // urls
    XYPlot xyPlot = jFreeChart.getXYPlot();
    XYItemRenderer xyItemRenderer = xyPlot.getRenderer();
    int limit = xySeriesCollection.getSeriesCount();
    for (int index = 0; index < limit; ++index) {
      VisualRow visualRow = visualSet.getVisualRow(index);
      xyItemRenderer.setSeriesPaint(index, visualRow.getColor());
      xyItemRenderer.setSeriesStroke(index, visualRow.getStroke());
    }
    return jFreeChart;
  }

  public static JFreeChart of(VisualSet visualSet) {
    return of(visualSet, false);
  }
}
