// code by jph
package ch.alpine.bridge.fig;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import ch.alpine.tensor.DoubleScalar;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.alg.Subdivide;
import ch.alpine.tensor.api.ScalarUnaryOperator;
import ch.alpine.tensor.pdf.InverseCDF;
import ch.alpine.tensor.pdf.RandomVariate;
import ch.alpine.tensor.pdf.UnivariateDistribution;
import ch.alpine.tensor.pdf.c.HistogramDistribution;
import ch.alpine.tensor.pdf.c.NormalDistribution;
import ch.alpine.tensor.pdf.c.UniformDistribution;
import ch.alpine.tensor.qty.Quantity;
import demo.tensor.pdf.TrapezoidalDistributionDemo;

class ListPlotTest {
  @Test
  void testEmpty() {
    Show visualSet = new Show();
    ListPlot.of(visualSet.setJoined(true));
  }

  @Test
  void testEmptyRow() {
    Show visualSet = new Show();
    VisualRow visualRow = visualSet.add(Tensors.empty(), Tensors.empty());
    visualRow.setLabel("empty");
    visualSet.add(Tensors.vector(1, 2, 5), Tensors.vector(2, 2.2, -1.6));
    CascadeHelper.draw(ListPlot.of(visualSet.setJoined(true)));
    CascadeHelper.draw(ListPlot.of(visualSet.setJoined(false)));
  }

  private static final ScalarUnaryOperator suoX = s -> Quantity.of(s, "s");
  private static final ScalarUnaryOperator suoY = s -> Quantity.of(s, "m");

  @Test
  void testUnitsX() {
    Show visualSet = new Show();
    VisualRow visualRow = visualSet.add(Tensors.empty(), Tensors.empty());
    visualRow.setLabel("empty");
    visualSet.add(Tensors.empty());
    visualSet.add(Tensors.vector(1, 2, 5).map(suoX), Tensors.vector(2, 2.2, -1.6));
    visualSet.add(Tensors.empty());
    visualSet.add(Tensors.vector(0, 2, 5).map(suoX), Tensors.vector(1, 2, 1.6));
    visualSet.add(Tensors.empty());
    CascadeHelper.draw(ListPlot.of(visualSet));
  }

  @Test
  void testUnitsY() {
    Show visualSet = new Show();
    VisualRow visualRow = visualSet.add(Tensors.empty(), Tensors.empty());
    visualRow.setLabel("empty");
    visualSet.add(Tensors.empty());
    visualSet.add(Tensors.vector(1, 2, 5), Tensors.vector(2, 2.2, -1.6).map(suoY));
    visualSet.add(Tensors.empty());
    visualSet.add(Tensors.vector(3, 5), Tensors.vector(1, 2.6).map(suoY));
    CascadeHelper.draw(ListPlot.of(visualSet));
  }

  @Test
  void testAlreadyLarge() {
    Show visualSet = new Show();
    int n = 100_000; // tested for up to 10 million
    Tensor points = RandomVariate.of(UniformDistribution.of(Quantity.of(1, "m"), Quantity.of(10, "m")), n, 2);
    visualSet.add(points);
    ListPlot.of(visualSet);
  }

  @Test
  void testAlreadyLargeNaN() {
    Random random = new Random();
    Show visualSet = new Show();
    int n = 100_000; // tested for up to 10 million
    Tensor points = RandomVariate.of(UniformDistribution.of(Quantity.of(1, "m"), Quantity.of(10, "m")), n, 2);
    for (int count = 0; count < 10000; ++count)
      points.set(Quantity.of(DoubleScalar.INDETERMINATE, "m"), random.nextInt(n), random.nextInt(2));
    visualSet.add(points);
    ListPlot.of(visualSet);
  }

  @Test
  void testDistribution(@TempDir File folder) throws IOException {
    Showable jFreeChart = TrapezoidalDistributionDemo.generate();
    Show.export(new File(folder, "trap_distr.png"), jFreeChart, new Dimension(640, 480));
  }

  @Test
  void testSome(@TempDir File folder) throws IOException {
    Tensor values1 = RandomVariate.of(UniformDistribution.unit(), 5);
    Tensor values2 = RandomVariate.of(UniformDistribution.unit(), 15);
    Tensor values3 = RandomVariate.of(UniformDistribution.unit(), 10);
    Show visualSet = new Show();
    visualSet.setPlotLabel("List Plot Demo");
    Tensor domain1 = RandomVariate.of(UniformDistribution.unit(), values1.length());
    ScalarUnaryOperator suoX = s -> Quantity.of(s.add(RealScalar.of(100)), "s");
    ScalarUnaryOperator suoY = s -> Quantity.of(s.add(RealScalar.of(300)), "m");
    VisualRow visualRow1 = visualSet.add(domain1.map(suoX), values1.map(suoY));
    visualRow1.setLabel("first");
    Tensor domain2 = RandomVariate.of(UniformDistribution.unit(), values2.length());
    VisualRow visualRow2 = visualSet.add(domain2.map(suoX), values2.map(suoY));
    Tensor domain3 = RandomVariate.of(UniformDistribution.unit(), values3.length());
    visualSet.add(domain3.map(suoX), values3.map(suoY));
    Tensor domain4 = Tensors.vector(1, 3, 2, 5, 4).multiply(RealScalar.of(0.2));
    visualSet.add(domain4.map(suoX), domain4.map(suoY));
    // ChartFactory.setChartTheme(ChartTheme.STANDARD);
    Showable jFreeChart = ListPlot.of(visualSet.setJoined(true));
    File file = new File(folder, ListPlot.class.getSimpleName() + ".png");
    Show.export(file, jFreeChart, new Dimension(500, 300));
  }

  @Test
  void testDistrib1(@TempDir File folder) throws IOException {
    UnivariateDistribution dist = (UnivariateDistribution) NormalDistribution.of(1, 2);
    HistogramDistribution distribution = (HistogramDistribution) //
    HistogramDistribution.of(RandomVariate.of(dist, 2000), RealScalar.of(0.25));
    {
      Tensor domain = Subdivide.of(-5, 8, 300);
      Show visualSet = new Show();
      visualSet.add(domain, domain.map(distribution::at));
      visualSet.add(domain, domain.map(distribution::p_lessEquals));
      visualSet.add(domain, domain.map(dist::at));
      Showable jFreeChart = ListPlot.of(visualSet.setJoined(true));
      Show.export(new File(folder, "hd.png"), jFreeChart, new Dimension(640, 480));
    }
  }

  @Test
  void testDistrib2(@TempDir File folder) throws IOException {
    UnivariateDistribution dist = (UnivariateDistribution) NormalDistribution.of(1, 2);
    HistogramDistribution distribution = (HistogramDistribution) //
    HistogramDistribution.of(RandomVariate.of(dist, 2000), RealScalar.of(0.25));
    {
      Tensor domain = Subdivide.of(0, 1, 300);
      InverseCDF inv1 = InverseCDF.of(distribution);
      InverseCDF inv2 = InverseCDF.of(dist);
      Show visualSet = new Show();
      visualSet.add(domain, domain.map(inv1::quantile));
      visualSet.add(domain, domain.map(inv2::quantile));
      Showable jFreeChart = ListPlot.of(visualSet.setJoined(true));
      Show.export(new File(folder, "hd_inv.png"), jFreeChart, new Dimension(640, 480));
    }
  }
}
