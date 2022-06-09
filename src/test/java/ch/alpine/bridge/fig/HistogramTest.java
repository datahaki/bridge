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
import ch.alpine.tensor.alg.Range;
import ch.alpine.tensor.alg.Subdivide;
import ch.alpine.tensor.ext.HomeDirectory;
import ch.alpine.tensor.pdf.CDF;
import ch.alpine.tensor.pdf.Distribution;
import ch.alpine.tensor.pdf.InverseCDF;
import ch.alpine.tensor.pdf.PDF;
import ch.alpine.tensor.pdf.RandomVariate;
import ch.alpine.tensor.pdf.TruncatedDistribution;
import ch.alpine.tensor.pdf.UnivariateDistribution;
import ch.alpine.tensor.pdf.c.HistogramDistribution;
import ch.alpine.tensor.pdf.c.NormalDistribution;
import ch.alpine.tensor.pdf.d.PoissonDistribution;
import ch.alpine.tensor.sca.Clips;
import demo.tensor.pdf.TruncatedDiscreteDemo;

class HistogramTest {
  @Test
  public void testEmpty() {
    VisualSet visualSet = new VisualSet();
    TestHelper.draw(Histogram.of(visualSet));
  }

  @Test
  public void testEmptyRow() {
    VisualSet visualSet = new VisualSet();
    visualSet.add(Tensors.empty());
    TestHelper.draw(Histogram.of(visualSet));
  }

  @Test
  public void testQuantity() throws IOException {
    VisualSet visualSet = new VisualSet();
    visualSet.add(Tensors.fromString("{{2[m],3[s]}, {4[m],5[s]}, {5[m],1[s]}}"));
    JFreeChart jFreeChart = Histogram.of(visualSet);
    ChartUtils.saveChartAsPNG(HomeDirectory.Pictures("histunit.png"), jFreeChart, 640, 480);
    TestHelper.draw(jFreeChart);
  }

  @Test
  public void testDistrib(@TempDir File folder) throws IOException {
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
  public void testDistrib2(@TempDir File folder) throws IOException {
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
  public void testTruncated(@TempDir File folder) throws IOException {
    Distribution original = PoissonDistribution.of(7);
    Distribution distribution = TruncatedDistribution.of(original, Clips.interval(5, 10));
    PDF pdf = PDF.of(distribution);
    CDF cdf = CDF.of(distribution);
    PDF pdf_o = PDF.of(original);
    VisualSet visualSet = new VisualSet();
    {
      Tensor domain = Range.of(0, 12);
      visualSet.add(domain, domain.map(pdf::at));
      visualSet.add(domain, domain.map(cdf::p_lessEquals));
      visualSet.add(domain, domain.map(pdf_o::at));
    }
    JFreeChart jFreeChart = Histogram.of(visualSet);
    jFreeChart.setBackgroundPaint(Color.WHITE);
    ChartUtils.saveChartAsPNG(new File(folder, //
        TruncatedDiscreteDemo.class.getSimpleName() + ".png"), jFreeChart, 640, 480);
  }
}
