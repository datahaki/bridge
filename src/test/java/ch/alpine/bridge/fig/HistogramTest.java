// code by jph
package ch.alpine.bridge.fig;

import java.awt.Color;
import java.io.File;
import java.io.IOException;

import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.alg.Subdivide;
import ch.alpine.tensor.pdf.InverseCDF;
import ch.alpine.tensor.pdf.RandomVariate;
import ch.alpine.tensor.pdf.UnivariateDistribution;
import ch.alpine.tensor.pdf.c.HistogramDistribution;
import ch.alpine.tensor.pdf.c.NormalDistribution;
import demo.tensor.pdf.TruncatedDiscreteDemo;

class HistogramTest {
  @Test
  void testEmpty() {
    VisualSet visualSet = new VisualSet();
    CascadeHelper.draw(Histogram.of(visualSet));
  }

  @Test
  void testEmptyRow() {
    VisualSet visualSet = new VisualSet();
    visualSet.add(Tensors.empty());
    CascadeHelper.draw(Histogram.of(visualSet));
  }

  @Test
  void testQuantity(@TempDir File folder) throws IOException {
    VisualSet visualSet = new VisualSet();
    visualSet.add(Tensors.fromString("{{2[m],3[s]}, {4[m],5[s]}, {5[m],1[s]}}"));
    JFreeChart jFreeChart = Histogram.of(visualSet);
    ChartUtils.saveChartAsPNG(new File(folder, "histunit.png"), jFreeChart, 640, 480);
    CascadeHelper.draw(jFreeChart);
  }

  @Test
  void testDistrib1(@TempDir File folder) throws IOException {
    UnivariateDistribution dist = (UnivariateDistribution) NormalDistribution.of(1, 2);
    HistogramDistribution distribution = (HistogramDistribution) //
    HistogramDistribution.of(RandomVariate.of(dist, 2000), RealScalar.of(0.25));
    {
      Tensor domain = Subdivide.of(-5, 8, 300);
      VisualSet visualSet = new VisualSet();
      visualSet.add(domain, domain.map(distribution::at));
      visualSet.add(domain, domain.map(distribution::p_lessEquals));
      visualSet.add(domain, domain.map(dist::at));
      JFreeChart jFreeChart = ListPlot.of(visualSet, true);
      jFreeChart.setBackgroundPaint(Color.WHITE);
      ChartUtils.saveChartAsPNG(new File(folder, "hd.png"), jFreeChart, 640, 480);
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
      VisualSet visualSet = new VisualSet();
      visualSet.add(domain, domain.map(inv1::quantile));
      visualSet.add(domain, domain.map(inv2::quantile));
      JFreeChart jFreeChart = ListPlot.of(visualSet, true);
      jFreeChart.setBackgroundPaint(Color.WHITE);
      ChartUtils.saveChartAsPNG(new File(folder, "hd_inv.png"), jFreeChart, 640, 480);
    }
  }

  @Test
  void testTruncated(@TempDir File folder) throws IOException {
    JFreeChart jFreeChart = TruncatedDiscreteDemo.generate();
    ChartUtils.saveChartAsPNG(new File(folder, "truncated.png"), jFreeChart, 640, 480);
  }
}
