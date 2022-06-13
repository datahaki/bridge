// code by jph
package ch.alpine.bridge.fig;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import ch.alpine.tensor.DoubleScalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.api.ScalarUnaryOperator;
import ch.alpine.tensor.pdf.RandomVariate;
import ch.alpine.tensor.pdf.c.UniformDistribution;
import ch.alpine.tensor.qty.Quantity;
import demo.tensor.pdf.TrapezoidalDistributionDemo;

class ListPlotTest {
  @Test
  void testEmpty() {
    VisualSet visualSet = new VisualSet();
    ListPlot.of(visualSet, true);
  }

  @Test
  void testEmptyRow() {
    VisualSet visualSet = new VisualSet();
    VisualRow visualRow = visualSet.add(Tensors.empty(), Tensors.empty());
    visualRow.setLabel("empty");
    visualSet.add(Tensors.vector(1, 2, 5), Tensors.vector(2, 2.2, -1.6));
    TestHelper.draw(ListPlot.of(visualSet, true));
    TestHelper.draw(ListPlot.of(visualSet, false));
  }

  private static final ScalarUnaryOperator suoX = s -> Quantity.of(s, "s");
  private static final ScalarUnaryOperator suoY = s -> Quantity.of(s, "m");

  @Test
  void testUnitsX() {
    VisualSet visualSet = new VisualSet();
    VisualRow visualRow = visualSet.add(Tensors.empty(), Tensors.empty());
    visualRow.setLabel("empty");
    visualSet.add(Tensors.empty());
    visualSet.add(Tensors.vector(1, 2, 5).map(suoX), Tensors.vector(2, 2.2, -1.6));
    visualSet.add(Tensors.empty());
    visualSet.add(Tensors.vector(0, 2, 5).map(suoX), Tensors.vector(1, 2, 1.6));
    visualSet.add(Tensors.empty());
    TestHelper.draw(ListPlot.of(visualSet, true));
  }

  @Test
  void testUnitsY() {
    VisualSet visualSet = new VisualSet();
    VisualRow visualRow = visualSet.add(Tensors.empty(), Tensors.empty());
    visualRow.setLabel("empty");
    visualSet.add(Tensors.empty());
    visualSet.add(Tensors.vector(1, 2, 5), Tensors.vector(2, 2.2, -1.6).map(suoY));
    visualSet.add(Tensors.empty());
    visualSet.add(Tensors.vector(3, 5), Tensors.vector(1, 2.6).map(suoY));
    TestHelper.draw(ListPlot.of(visualSet, true));
  }

  @Test
  void testAlreadyLarge() {
    VisualSet visualSet = new VisualSet();
    int n = 100_000; // tested for up to 10 million
    Tensor points = RandomVariate.of(UniformDistribution.of(Quantity.of(1, "m"), Quantity.of(10, "m")), n, 2);
    visualSet.add(points);
    ListPlot.of(visualSet);
  }

  @Test
  void testAlreadyLargeNaN() {
    Random random = new Random();
    VisualSet visualSet = new VisualSet();
    int n = 100_000; // tested for up to 10 million
    Tensor points = RandomVariate.of(UniformDistribution.of(Quantity.of(1, "m"), Quantity.of(10, "m")), n, 2);
    for (int count = 0; count < 10000; ++count)
      points.set(Quantity.of(DoubleScalar.INDETERMINATE, "m"), random.nextInt(n), random.nextInt(2));
    visualSet.add(points);
    ListPlot.of(visualSet);
  }

  @Test
  void testDistribution(@TempDir File folder) throws IOException {
    JFreeChart jFreeChart = TrapezoidalDistributionDemo.generate();
    ChartUtils.saveChartAsPNG(new File(folder, "trap_distr.png"), jFreeChart, 640, 480);
  }
}
