// code by jph
package ch.alpine.bridge.usr;

import java.awt.Color;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class ChartPanelDemo implements Runnable {
  private final JFrame jFrame = new JFrame();
  private final XYSeries xySeries = new XYSeries("some", false);

  public ChartPanelDemo() {
    jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    xySeries.add(2, 2);
    xySeries.add(3, 1);
    xySeries.add(4, 3);
    XYSeriesCollection xySeriesCollection = new XYSeriesCollection();
    xySeriesCollection.addSeries(xySeries);
    // XYSeriesCollection xySeriesCollection = DatasetFactory.xySeriesCollection(visualSet);
    JFreeChart jFreeChart = ChartFactory.createXYLineChart( //
        "label", //
        "labX", //
        "labY", //
        xySeriesCollection, PlotOrientation.VERTICAL, //
        false, // legend
        false, // tooltips
        false); // urls
    jFreeChart.setBackgroundPaint(Color.WHITE);
    ChartPanel chartPanel = new ChartPanel(jFreeChart);
    jFrame.setContentPane(chartPanel);
    jFrame.setBounds(100, 100, 600, 600);
    jFrame.setVisible(true);
  }

  @Override
  public void run() {
    int count = 0;
    Random random = new Random();
    while (jFrame.isVisible())
      try {
        xySeries.add(3 + count, random.nextDouble(), false);
        xySeries.remove(0);
        // xySeries.delete(0, 0);
        ++count;
        Thread.sleep(100);
      } catch (Exception exception) {
        // ---
      }
  }

  public static void main(String[] args) {
    new Thread(new ChartPanelDemo()).start();
  }
}
