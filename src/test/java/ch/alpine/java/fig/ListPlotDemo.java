// code by jph
package ch.alpine.java.fig;

import java.awt.Color;
import java.io.File;
import java.io.IOException;

import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;

import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.api.ScalarUnaryOperator;
import ch.alpine.tensor.ext.HomeDirectory;
import ch.alpine.tensor.pdf.RandomVariate;
import ch.alpine.tensor.pdf.UniformDistribution;
import ch.alpine.tensor.qty.Quantity;

/* package */ enum ListPlotDemo {
  ;
  private static final ScalarUnaryOperator suoX = s -> Quantity.of(s.add(RealScalar.of(100)), "s");
  private static final ScalarUnaryOperator suoY = s -> Quantity.of(s.add(RealScalar.of(300)), "m");

  public static void main(String[] args) throws IOException {
    Tensor values1 = RandomVariate.of(UniformDistribution.unit(), 5);
    Tensor values2 = RandomVariate.of(UniformDistribution.unit(), 15);
    Tensor values3 = RandomVariate.of(UniformDistribution.unit(), 10);
    VisualSet visualSet = new VisualSet();
    visualSet.setPlotLabel("List Plot Demo");
    Tensor domain1 = RandomVariate.of(UniformDistribution.unit(), values1.length());
    VisualRow visualRow1 = visualSet.add(domain1.map(suoX), values1.map(suoY));
    visualRow1.setLabel("first");
    Tensor domain2 = RandomVariate.of(UniformDistribution.unit(), values2.length());
    VisualRow visualRow2 = visualSet.add(domain2.map(suoX), values2.map(suoY));
    visualRow2.setAutoSort(true);
    Tensor domain3 = RandomVariate.of(UniformDistribution.unit(), values3.length());
    visualSet.add(domain3.map(suoX), values3.map(suoY));
    Tensor domain4 = Tensors.vector(1, 3, 2, 5, 4).multiply(RealScalar.of(0.2));
    visualSet.add(domain4.map(suoX), domain4.map(suoY));
    /* amodeus specific */
    // ChartFactory.setChartTheme(ChartTheme.STANDARD);
    {
      JFreeChart jFreeChart = ListPlot.of(visualSet, true);
      jFreeChart.setBackgroundPaint(Color.WHITE);
      File file = HomeDirectory.Pictures(ListPlot.class.getSimpleName() + ".png");
      ChartUtils.saveChartAsPNG(file, jFreeChart, 500, 300);
    }
  }
}
