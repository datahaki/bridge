// code by jph
package demo.tensor.pdf;

import java.awt.Color;
import java.io.IOException;

import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;

import ch.alpine.java.fig.ListPlot;
import ch.alpine.java.fig.VisualSet;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.alg.Subdivide;
import ch.alpine.tensor.ext.HomeDirectory;
import ch.alpine.tensor.pdf.CDF;
import ch.alpine.tensor.pdf.Distribution;
import ch.alpine.tensor.pdf.PDF;
import ch.alpine.tensor.pdf.TruncatedDistribution;
import ch.alpine.tensor.pdf.c.NormalDistribution;
import ch.alpine.tensor.sca.Clips;

public enum TruncatedDistributionDemo {
  ;
  public static void main(String[] args) throws IOException {
    Distribution original = NormalDistribution.standard();
    Distribution distribution = TruncatedDistribution.of(original, Clips.interval(-1, 2.5));
    PDF pdf = PDF.of(distribution);
    CDF cdf = CDF.of(distribution);
    PDF pdf_o = PDF.of(original);
    VisualSet visualSet = new VisualSet();
    {
      Tensor domain = Subdivide.of(-3, 3, 100);
      visualSet.add(domain, domain.map(pdf::at));
      visualSet.add(domain, domain.map(cdf::p_lessEquals));
      visualSet.add(domain, domain.map(pdf_o::at));
    }
    JFreeChart jFreeChart = ListPlot.of(visualSet, true);
    jFreeChart.setBackgroundPaint(Color.WHITE);
    ChartUtils.saveChartAsPNG(HomeDirectory.Pictures("truncated.png"), jFreeChart, 640, 480);
  }
}
