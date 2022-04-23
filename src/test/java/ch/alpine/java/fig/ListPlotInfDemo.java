// code by jph
package ch.alpine.java.fig;

import java.awt.Color;
import java.io.File;
import java.io.IOException;

import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;

import ch.alpine.tensor.DoubleScalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.alg.Range;
import ch.alpine.tensor.ext.HomeDirectory;
import ch.alpine.tensor.pdf.RandomVariate;
import ch.alpine.tensor.pdf.c.UniformDistribution;

/* package */ enum ListPlotInfDemo {
  ;
  public static void main(String[] args) throws IOException {
    Tensor valuesX = Range.of(0, 10);
    Tensor valuesY = RandomVariate.of(UniformDistribution.unit(), 10);
    valuesY.set(DoubleScalar.POSITIVE_INFINITY, 3);
    VisualSet visualSet = new VisualSet();
    visualSet.setPlotLabel("List Plot Demo");
    VisualRow visualRow1 = visualSet.add(valuesX, valuesY);
    visualRow1.setLabel("first");
    {
      JFreeChart jFreeChart = ListPlot.of(visualSet, true);
      jFreeChart.setBackgroundPaint(Color.WHITE);
      File file = HomeDirectory.Pictures(ListPlotInfDemo.class.getSimpleName() + ".png");
      ChartUtils.saveChartAsPNG(file, jFreeChart, 500, 300);
    }
  }
}
