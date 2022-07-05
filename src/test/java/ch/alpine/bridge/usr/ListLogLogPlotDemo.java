// code by jph
package ch.alpine.bridge.usr;

import java.awt.Color;
import java.io.File;
import java.io.IOException;

import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;

import ch.alpine.bridge.fig.ListLogLogPlot;
import ch.alpine.bridge.fig.VisualSet;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.alg.Range;
import ch.alpine.tensor.ext.HomeDirectory;
import ch.alpine.tensor.sca.pow.Power;

public enum ListLogLogPlotDemo {
  ;
  public static JFreeChart create() {
    Tensor domain = Range.of(1, 21);
    VisualSet visualSet = new VisualSet();
    visualSet.setPlotLabel(ListLogLogPlot.class.getSimpleName());
    visualSet.add(domain, domain.map(Power.function(3)));
    return ListLogLogPlot.of(visualSet);
  }

  public static void main(String[] args) throws IOException {
    JFreeChart jFreeChart = create();
    jFreeChart.setBackgroundPaint(Color.WHITE);
    File file = HomeDirectory.Pictures(ListLogLogPlotDemo.class.getSimpleName() + ".png");
    ChartUtils.saveChartAsPNG(file, jFreeChart, DemoHelper.DEMO_W, DemoHelper.DEMO_H);
  }
}
