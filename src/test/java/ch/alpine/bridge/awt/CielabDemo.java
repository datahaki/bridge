// code by jph
package ch.alpine.bridge.awt;

import java.awt.Color;
import java.io.IOException;

import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;

import ch.alpine.bridge.fig.ListPlot;
import ch.alpine.bridge.fig.VisualSet;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.alg.Subdivide;
import ch.alpine.tensor.ext.HomeDirectory;

public enum CielabDemo {
  ;
  public static void main(String[] args) throws IOException {
    Tensor domain = Subdivide.of(0, 1, 200);
    VisualSet visualSet = new VisualSet();
    visualSet.add(domain.map(Cielabf::forward), domain);
    visualSet.add(domain, domain.map(Cielabf::inverse));
    JFreeChart jFreeChart = ListPlot.of(visualSet, true);
    jFreeChart.setBackgroundPaint(Color.WHITE);
    ChartUtils.saveChartAsPNG(HomeDirectory.Pictures("cielab.png"), jFreeChart, 400, 400);
  }
}
