// code by jph
package ch.alpine.bridge.usr;

import java.awt.Color;
import java.io.IOException;

import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;

import ch.alpine.bridge.fig.ListPlot;
import ch.alpine.bridge.fig.VisualSet;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.alg.Array;
import ch.alpine.tensor.alg.Dimensions;
import ch.alpine.tensor.alg.Range;
import ch.alpine.tensor.alg.Subdivide;
import ch.alpine.tensor.ext.HomeDirectory;
import ch.alpine.tensor.io.Export;
import ch.alpine.tensor.io.Import;
import ch.alpine.tensor.red.Max;
import ch.alpine.tensor.sca.Round;

enum ColorExtractDemo {
  ;
  public static void main(String[] args) throws IOException {
    Tensor tensor = Import.of(HomeDirectory.Documents("temperature.png"));
    System.out.println(Dimensions.of(tensor));
    Tensor rgba = tensor.get(0);
    // System.out.println(Pretty.of(rgba));
    Tensor domain = Range.of(0, rgba.length());
    Tensor sample = Subdivide.of(27 * 0.5, 1142 - 27 * 0.5, 41).map(Round.FUNCTION);
    System.out.println(sample.length());
    Tensor result = Array.zeros(42, 4);
    for (int i = 0; i < 3; ++i) {
      VisualSet visualSet = new VisualSet();
      Tensor intense = rgba.get(Tensor.ALL, i);
      visualSet.add(domain, intense);
      Tensor max = Tensors.empty();
      for (int j = 0; j < sample.length(); ++j) {
        Tensor col = Tensors.empty();
        for (int k = -5; k <= 5; ++k)
          col.append(intense.Get(sample.Get(j).number().intValue() + k));
        Tensor win = col.stream().reduce(Max::of).get();
        max.append(win);
        result.set(win, j, i);
      }
      visualSet.add(sample, max);
      JFreeChart jFreeChart = ListPlot.of(visualSet, true);
      jFreeChart.setBackgroundPaint(Color.WHITE);
      ChartUtils.saveChartAsPNG(HomeDirectory.Pictures("temp" + i + ".png"), jFreeChart, 640, 480);
    }
    for (int j = 0; j < sample.length(); ++j)
      result.set(RealScalar.of(255), j, 3);
    Export.of(HomeDirectory.file("cold_hot.csv"), result);
  }
}