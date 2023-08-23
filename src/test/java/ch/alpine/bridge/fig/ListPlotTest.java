// code by jph
package ch.alpine.bridge.fig;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.random.RandomGenerator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import ch.alpine.bridge.lang.SI;
import ch.alpine.tensor.DoubleScalar;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.alg.Subdivide;
import ch.alpine.tensor.alg.Transpose;
import ch.alpine.tensor.api.ScalarUnaryOperator;
import ch.alpine.tensor.pdf.Distribution;
import ch.alpine.tensor.pdf.InverseCDF;
import ch.alpine.tensor.pdf.RandomVariate;
import ch.alpine.tensor.pdf.UnivariateDistribution;
import ch.alpine.tensor.pdf.c.HistogramDistribution;
import ch.alpine.tensor.pdf.c.NormalDistribution;
import ch.alpine.tensor.pdf.c.UniformDistribution;
import ch.alpine.tensor.qty.DateTime;
import ch.alpine.tensor.qty.Quantity;

class ListPlotTest {
  @Test
  void testEmptyRow() {
    Show show = new Show();
    Showable showable = show.add(ListLinePlot.of(Tensors.empty(), Tensors.empty()));
    showable.setLabel("empty");
    show.add(ListLinePlot.of(Tensors.vector(1, 2, 5), Tensors.vector(2, 2.2, -1.6)));
    // CascadeHelper.draw(ListPlot.of(show.setJoined(true)));
    // CascadeHelper.draw(ListPlot.of(show.setJoined(false)));
  }

  @Test
  void testUnitsX() {
    Show show = new Show();
    Showable showable = show.add(ListLinePlot.of(Tensors.empty(), Tensors.empty()));
    showable.setLabel("empty");
    show.add(ListLinePlot.of(Tensors.empty()));
    show.add(ListLinePlot.of(Tensors.vector(1, 2, 5).map(SI.SECONDS::quantity), Tensors.vector(2, 2.2, -1.6)));
    show.add(ListLinePlot.of(Tensors.empty()));
    show.add(ListLinePlot.of(Tensors.vector(0, 2, 5).map(SI.SECONDS::quantity), Tensors.vector(1, 2, 1.6)));
    show.add(ListLinePlot.of(Tensors.empty()));
    // CascadeHelper.draw(ListPlot.of(show));
  }

  @Test
  void testUnitsY() {
    Show show = new Show();
    Showable showable = show.add(ListLinePlot.of(Tensors.empty(), Tensors.empty()));
    showable.setLabel("empty");
    show.add(ListLinePlot.of(Tensors.empty()));
    show.add(ListLinePlot.of(Tensors.vector(1, 2, 5), Tensors.vector(2, 2.2, -1.6).map(SI.METERS::quantity)));
    show.add(ListLinePlot.of(Tensors.empty()));
    show.add(ListLinePlot.of(Tensors.vector(3, 5), Tensors.vector(1, 2.6).map(SI.METERS::quantity)));
    // CascadeHelper.draw(ListPlot.of(show));
  }

  @Test
  void testAlreadyLarge() {
    Show visualSet = new Show();
    int n = 100_000; // tested for up to 10 million
    Tensor points = RandomVariate.of(UniformDistribution.of(Quantity.of(1, "m"), Quantity.of(10, "m")), n, 2);
    visualSet.add(ListLinePlot.of(points));
  }

  @Test
  void testAlreadyLargeNaN() {
    RandomGenerator random = new SecureRandom();
    Show visualSet = new Show();
    int n = 100_000; // tested for up to 10 million
    Tensor points = RandomVariate.of(UniformDistribution.of(Quantity.of(1, "m"), Quantity.of(10, "m")), n, 2);
    for (int count = 0; count < 10000; ++count)
      points.set(Quantity.of(DoubleScalar.INDETERMINATE, "m"), random.nextInt(n), random.nextInt(2));
    visualSet.add(ListLinePlot.of(points));
  }

  @Test
  void testSome(@TempDir File folder) throws IOException {
    Tensor values1 = RandomVariate.of(UniformDistribution.unit(), 5);
    Tensor values2 = RandomVariate.of(UniformDistribution.unit(), 15);
    Tensor values3 = RandomVariate.of(UniformDistribution.unit(), 10);
    Show show = new Show();
    show.setPlotLabel("List Plot Demo");
    Tensor domain1 = RandomVariate.of(UniformDistribution.unit(), values1.length());
    ScalarUnaryOperator suoX = s -> Quantity.of(s.add(RealScalar.of(100)), "s");
    ScalarUnaryOperator suoY = s -> Quantity.of(s.add(RealScalar.of(300)), "m");
    Showable showable1 = show.add(ListLinePlot.of(domain1.map(suoX), values1.map(suoY)));
    showable1.setLabel("first");
    Tensor domain2 = RandomVariate.of(UniformDistribution.unit(), values2.length());
    show.add(ListLinePlot.of(domain2.map(suoX), values2.map(suoY)));
    Tensor domain3 = RandomVariate.of(UniformDistribution.unit(), values3.length());
    show.add(ListLinePlot.of(domain3.map(suoX), values3.map(suoY)));
    Tensor domain4 = Tensors.vector(1, 3, 2, 5, 4).multiply(RealScalar.of(0.2));
    show.add(ListLinePlot.of(domain4.map(suoX), domain4.map(suoY)));
    // ChartFactory.setChartTheme(ChartTheme.STANDARD);
    File file = new File(folder, ListPlot.class.getSimpleName() + ".png");
    show.export(file, new Dimension(500, 300));
  }

  @Test
  void testDistrib1(@TempDir File folder) throws IOException {
    UnivariateDistribution dist = (UnivariateDistribution) NormalDistribution.of(1, 2);
    HistogramDistribution distribution = (HistogramDistribution) //
    HistogramDistribution.of(RandomVariate.of(dist, 2000), RealScalar.of(0.25));
    {
      Tensor domain = Subdivide.of(-5, 8, 300);
      Show show = new Show();
      show.add(ListLinePlot.of(domain, domain.map(distribution::at)));
      show.add(ListLinePlot.of(domain, domain.map(distribution::p_lessEquals)));
      show.add(ListLinePlot.of(domain, domain.map(dist::at)));
      show.export(new File(folder, "hd.png"), new Dimension(640, 480));
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
      Show show = new Show();
      show.add(ListLinePlot.of(domain, domain.map(inv1::quantile)));
      show.add(ListLinePlot.of(domain, domain.map(inv2::quantile)));
      show.export(new File(folder, "hd_inv.png"), new Dimension(640, 480));
    }
  }

  @Test
  void testConstructors() {
    Tensor domain = Tensors.fromString("{1, 2, 3, 4, 5}");
    Tensor values = RandomVariate.of(UniformDistribution.unit(), 5);
    Tensor points = Transpose.of(Tensors.of(domain, values));
    Show set2 = new Show();
    Showable row2 = set2.add(ListLinePlot.of(domain, values));
    row2.setLabel("row2");
    Showable row3 = set2.add(ListLinePlot.of(points));
    row3.setLabel("row3");
  }

  @Test
  void testAdd() {
    Tensor domain = Tensors.fromString("{1, 2, 3, 4, 5}");
    Tensor values1 = RandomVariate.of(UniformDistribution.unit(), 5);
    Tensor values2 = RandomVariate.of(UniformDistribution.unit(), 5);
    Show visualSet = new Show();
    visualSet.add(ListLinePlot.of(domain, values1));
    visualSet.add(ListLinePlot.of(domain, values2));
    // assertEquals(Dimensions.of(row1.points()), Dimensions.of(row2.points()));
  }

  @Test
  void testSetRowLabel() {
    Tensor domain = Tensors.fromString("{1, 2, 3, 4, 5}");
    Tensor values1 = RandomVariate.of(UniformDistribution.unit(), 5);
    Tensor values2 = RandomVariate.of(UniformDistribution.unit(), 5);
    Show set = new Show();
    Showable row1 = set.add(ListLinePlot.of(domain, values1));
    Showable row2 = set.add(ListLinePlot.of(domain, values2));
    row1.setLabel("row 1");
    row2.setLabel("row 2");
    assertEquals(row1.getLabel(), "row 1");
    assertEquals(row2.getLabel(), "row 2");
  }

  @Test
  void testEmptyPass(@TempDir File tempDir) throws IOException {
    Show show = new Show();
    show.add(ListLinePlot.of(Tensors.empty()));
    File file = new File(tempDir, "file.png");
    assertFalse(file.exists());
    show.export(file, new Dimension(100, 100));
    assertTrue(file.isFile());
    assertTrue(file.canWrite());
  }

  @Test
  void testDateTime() {
    Show visualSet = new Show();
    Distribution distribution = NormalDistribution.of(DateTime.now(), Quantity.of(3, "h"));
    Tensor points = RandomVariate.of(distribution, 10, 2);
    visualSet.add(ListLinePlot.of(points));
  }

  @Test
  void testPointNonMatrix() {
    assertThrows(Exception.class, () -> ListLinePlot.of(Tensors.vector(1, 2, 3, 4)));
    assertThrows(Exception.class, () -> ListLinePlot.of(RealScalar.ZERO));
  }

  @Test
  void testFailScalar() {
    assertThrows(Exception.class, () -> ListLinePlot.of(RealScalar.ZERO, RealScalar.ONE));
  }

  @Test
  void testFailVector() {
    assertThrows(Exception.class, () -> ListLinePlot.of(Tensors.vector(1, 2, 3, 4), Tensors.vector(1, 2, 3, 4, 5)));
  }

  @Test
  void testFailUnstructured() {
    assertThrows(Exception.class, () -> ListLinePlot.of(Tensors.fromString("{{1, 2}, {3, 4}, {5, 6}, {3}}")));
    assertThrows(Exception.class, () -> ListLinePlot.of(Tensors.fromString("{{1, 2}, {3, 4}, {5, 6}, 4}")));
    assertThrows(Exception.class, () -> ListLinePlot.of(Tensors.fromString("{{1, 2, 3}, {3, 4, 2}, {5, 6, 3}, {3, 5, 3}}")));
  }
}
