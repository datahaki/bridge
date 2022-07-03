// code by gjoel, jph
package ch.alpine.bridge.fig;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.LogarithmicAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.XYSeriesCollection;

import ch.alpine.bridge.fig.Axis.Type;

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
  /** Remark:
   * We would like to make joined property of VisualRow, but JFreeChart does not support
   * this granularity.
   * 
   * @param visualSet
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
    XYItemRenderer xyItemRenderer = xyPlot.getRenderer();
    int limit = xySeriesCollection.getSeriesCount();
    for (int index = 0; index < limit; ++index) {
      VisualRow visualRow = visualSet.getVisualRow(index);
      xyItemRenderer.setSeriesPaint(index, visualRow.getColor());
      xyItemRenderer.setSeriesStroke(index, visualRow.getStroke());
    }
    if (visualSet.getAxisX().getType().equals(Type.LOGARITHMIC)) {
      LogarithmicAxis logarithmicAxis = new LogarithmicAxis(visualSet.getAxisX().getAxisLabel());
      jFreeChart.getXYPlot().setDomainAxis(logarithmicAxis);
    }
    if (visualSet.getAxisY().getType().equals(Type.LOGARITHMIC)) {
      LogarithmicAxis logarithmicAxis = new LogarithmicAxis(visualSet.getAxisY().getAxisLabel());
      jFreeChart.getXYPlot().setRangeAxis(logarithmicAxis);
    }
    StaticHelper.setRange(visualSet.getAxisX(), jFreeChart.getXYPlot().getDomainAxis());
    StaticHelper.setRange(visualSet.getAxisY(), jFreeChart.getXYPlot().getRangeAxis());
    return jFreeChart;
  }

  /** Mathematica's default is to draw data points as separate dots,
   * i.e. "Joined->False".
   * 
   * Tested with up to 10 million points - a little slow but possible.
   * 
   * @param visualSet
   * @return */
  public static JFreeChart of(VisualSet visualSet) {
    return of(visualSet, false);
  }
}
