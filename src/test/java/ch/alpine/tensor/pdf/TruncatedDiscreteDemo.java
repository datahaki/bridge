// code by jph
package ch.alpine.tensor.pdf;

import java.awt.Color;
import java.io.IOException;

import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;

import ch.alpine.java.fig.Histogram;
import ch.alpine.java.fig.VisualSet;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.alg.Range;
import ch.alpine.tensor.ext.HomeDirectory;
import ch.alpine.tensor.pdf.d.PoissonDistribution;
import ch.alpine.tensor.sca.Clips;

public enum TruncatedDiscreteDemo {
  ;
  public static void main(String[] args) throws IOException {
    Distribution original = PoissonDistribution.of(7);
    Distribution distribution = TruncatedDistribution.of(original, Clips.interval(5, 10));
    System.out.println(distribution);
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
    ChartUtils.saveChartAsPNG(HomeDirectory.Pictures( //
        TruncatedDiscreteDemo.class.getSimpleName() + ".png"), jFreeChart, 640, 480);
  }
}
