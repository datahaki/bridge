// code by jph
package ch.alpine.bridge.usr;

import java.awt.Color;
import java.io.File;
import java.io.IOException;

import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;

import ch.alpine.bridge.fig.ListPlot;
import ch.alpine.bridge.fig.VisualSet;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.alg.Subdivide;
import ch.alpine.tensor.ext.HomeDirectory;
import ch.alpine.tensor.img.ColorDataGradients;
import ch.alpine.tensor.img.ColorDataLists;
import ch.alpine.tensor.sca.Clips;

/* package */ enum ListPlotDemo {
  ;
  public static void main(String[] args) throws IOException {
    Tensor domain = Subdivide.increasing(Clips.unit(), 50);
    Tensor rgba = domain.map(ColorDataGradients.CLASSIC);
    VisualSet visualSet = new VisualSet(ColorDataLists._109.strict().deriveWithAlpha(192));
    visualSet.setPlotLabel(ListPlot.class.getSimpleName());
    visualSet.add(domain, rgba.get(Tensor.ALL, 0)).setLabel("red");
    visualSet.add(domain, rgba.get(Tensor.ALL, 1)).setLabel("green");
    visualSet.add(domain, rgba.get(Tensor.ALL, 2)).setLabel("blue");
    // ListPlot.of(null)
    JFreeChart jFreeChart = ListPlot.of(visualSet);
    jFreeChart.setBackgroundPaint(Color.WHITE);
    File file = HomeDirectory.Pictures(ListPlot.class.getSimpleName() + ".png");
    ChartUtils.saveChartAsPNG(file, jFreeChart, DemoHelper.DEMO_W, DemoHelper.DEMO_H);
  }
}
