// code by jph
package ch.alpine.java.fig;

import java.awt.Color;
import java.io.IOException;

import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeriesCollection;

import ch.alpine.tensor.ext.HomeDirectory;
import ch.alpine.tensor.pdf.RandomVariate;
import ch.alpine.tensor.pdf.UniformDistribution;
import junit.framework.TestCase;

public class CustomPlotTest extends TestCase {
  public void testSimple() throws IOException {
    VisualSet visualSet = new VisualSet();
    VisualRow visualRow = visualSet.add(RandomVariate.of(UniformDistribution.unit(), 1000, 2));
    visualRow.setLabel("here");
    XYSeriesCollection xySeriesCollection = DatasetFactory.xySeriesCollection(visualSet);
    // JFreeChart jFreeChart = new JFreeChart(new CustomPlot());
    NumberAxis xAxis = new NumberAxis("xaxis");
    xAxis.setAutoRangeIncludesZero(false);
    NumberAxis yAxis = new NumberAxis("yaxis");
    XYItemRenderer renderer = new XYLineAndShapeRenderer(true, false);
    CustomPlot plot = new CustomPlot(xySeriesCollection, xAxis, yAxis, renderer);
    plot.setOrientation(PlotOrientation.VERTICAL);
    JFreeChart chart = new JFreeChart("title", JFreeChart.DEFAULT_TITLE_FONT, plot, visualSet.hasLegend());
    // DefaultChartTheme.STANDARD.apply(chart);
    chart.setBackgroundPaint(Color.WHITE);
    ChartUtils.saveChartAsPNG(HomeDirectory.Pictures("customplot.png"), chart, 1024, 320);
  }
}
