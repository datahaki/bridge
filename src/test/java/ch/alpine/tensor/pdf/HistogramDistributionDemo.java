// code by jph
package ch.alpine.tensor.pdf;

import java.awt.Color;
import java.io.IOException;

import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;

import ch.alpine.java.fig.ListPlot;
import ch.alpine.java.fig.VisualSet;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.alg.Subdivide;
import ch.alpine.tensor.ext.HomeDirectory;
import ch.alpine.tensor.pdf.c.HistogramDistribution;
import ch.alpine.tensor.pdf.c.NormalDistribution;

public enum HistogramDistributionDemo {
  ;
  public static void main(String[] args) throws IOException {
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
      ChartUtils.saveChartAsPNG(HomeDirectory.Pictures("hd.png"), jFreeChart, 640, 480);
    }
    {
      Tensor domain = Subdivide.of(0, 1, 300);
      InverseCDF inv1 = InverseCDF.of(distribution);
      InverseCDF inv2 = InverseCDF.of(dist);
      VisualSet visualSet = new VisualSet();
      visualSet.add(domain, domain.map(inv1::quantile));
      visualSet.add(domain, domain.map(inv2::quantile));
      JFreeChart jFreeChart = ListPlot.of(visualSet, true);
      jFreeChart.setBackgroundPaint(Color.WHITE);
      ChartUtils.saveChartAsPNG(HomeDirectory.Pictures("hd_inv.png"), jFreeChart, 640, 480);
    }
  }
}
