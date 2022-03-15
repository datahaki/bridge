// code by jph
package demo.tensor.pdf;

import java.awt.Color;
import java.io.IOException;

import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;

import ch.alpine.java.fig.ListPlot;
import ch.alpine.java.fig.VisualSet;
import ch.alpine.tensor.RationalScalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.alg.Range;
import ch.alpine.tensor.ext.HomeDirectory;
import ch.alpine.tensor.pdf.CDF;
import ch.alpine.tensor.pdf.Distribution;
import ch.alpine.tensor.pdf.PDF;
import ch.alpine.tensor.pdf.d.BinomialDistribution;

public enum BinomialDistributionDemo {
  ;
  public static void main(String[] args) throws IOException {
    int n = 50;
    Distribution distribution = BinomialDistribution.of(n, RationalScalar.HALF);
    PDF pdf = PDF.of(distribution);
    CDF cdf = CDF.of(distribution);
    VisualSet visualSet = new VisualSet();
    {
      Tensor domain = Range.of(0, n + 1);
      visualSet.add(domain, domain.map(pdf::at));
      visualSet.add(domain, domain.map(cdf::p_lessEquals));
    }
    JFreeChart jFreeChart = ListPlot.of(visualSet, true);
    jFreeChart.setBackgroundPaint(Color.WHITE);
    ChartUtils.saveChartAsPNG(HomeDirectory.Pictures("binomial_distr.png"), jFreeChart, 640, 480);
  }
}
