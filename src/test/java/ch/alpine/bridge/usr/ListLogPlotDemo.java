// code by jph
package ch.alpine.bridge.usr;

import java.awt.Color;
import java.io.File;
import java.io.IOException;

import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;

import ch.alpine.bridge.fig.ListLogPlot;
import ch.alpine.bridge.fig.VisualSet;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.alg.Range;
import ch.alpine.tensor.ext.HomeDirectory;
import ch.alpine.tensor.sca.gam.Factorial;

public enum ListLogPlotDemo {
  ;
  public static JFreeChart create() {
    Tensor domain = Range.of(1, 21);
    VisualSet visualSet = new VisualSet();
    visualSet.setPlotLabel(ListLogPlot.class.getSimpleName());
    visualSet.add(domain, domain.map(Factorial.FUNCTION));
    return ListLogPlot.of(visualSet, true);
  }

  public static JFreeChart create2() {
    Tensor domain = Range.of(1, 21);
    VisualSet visualSet = new VisualSet();
    visualSet.setPlotLabel(ListLogPlot.class.getSimpleName());
    visualSet.add(domain, domain.map(Factorial.FUNCTION));
    return ListLogPlot.of(visualSet);
  }

  public static void main(String[] args) throws IOException {
    JFreeChart jFreeChart = create();
    jFreeChart.setBackgroundPaint(Color.WHITE);
    File file = HomeDirectory.Pictures(ListLogPlotDemo.class.getSimpleName() + ".png");
    ChartUtils.saveChartAsPNG(file, jFreeChart, DemoHelper.DEMO_W, DemoHelper.DEMO_H);
  }
}
