// code by jph
package ch.alpine.bridge.usr;

import java.awt.Color;
import java.io.IOException;

import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;

import ch.alpine.bridge.fig.Histogram;
import ch.alpine.bridge.fig.VisualSet;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.ext.HomeDirectory;

public enum HistogramDemo {
  ;
  public static void main(String[] args) throws IOException {
    VisualSet visualSet = new VisualSet();
    visualSet.add(Tensors.fromString("{{2[m],3[s]}, {3[m],0[s]}, {4[m],3[s]}, {5[m],1[s]}}")).setLabel("first");
    visualSet.add(Tensors.fromString("{{3[m],2[s]}, {4[m],2.5[s]}, {5[m],2[s]}}")).setLabel("second");
    visualSet.setPlotLabel(Histogram.class.getSimpleName());
    JFreeChart jFreeChart = Histogram.of(visualSet);
    jFreeChart.setBackgroundPaint(Color.WHITE);
    ChartUtils.saveChartAsPNG( //
        HomeDirectory.Pictures(Histogram.class.getSimpleName() + ".png"), //
        jFreeChart, //
        DemoHelper.DEMO_W, //
        DemoHelper.DEMO_H);
  }
}
