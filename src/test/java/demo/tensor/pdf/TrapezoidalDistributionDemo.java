// code by jph
package demo.tensor.pdf;

import java.awt.Color;
import java.io.IOException;

import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;

import ch.alpine.bridge.fig.ListPlot;
import ch.alpine.bridge.fig.VisualSet;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.alg.Subdivide;
import ch.alpine.tensor.api.ScalarTensorFunction;
import ch.alpine.tensor.ext.HomeDirectory;
import ch.alpine.tensor.itp.BSplineFunctionString;
import ch.alpine.tensor.pdf.CDF;
import ch.alpine.tensor.pdf.Distribution;
import ch.alpine.tensor.pdf.PDF;
import ch.alpine.tensor.pdf.c.TrapezoidalDistribution;

public enum TrapezoidalDistributionDemo {
  ;
  public static JFreeChart generate() {
    Distribution distribution = TrapezoidalDistribution.of(0.5, 1.5, 1.5, 2.5);
    PDF pdf = PDF.of(distribution);
    CDF cdf = CDF.of(distribution);
    VisualSet visualSet = new VisualSet();
    {
      Tensor domain = Subdivide.of(0, 4, 100);
      visualSet.add(domain, domain.map(pdf::at));
      visualSet.add(domain, domain.map(cdf::p_lessEquals));
    }
    {
      Tensor sequence = Tensors.vector(0, 0, 1, 1);
      Tensor domain = Subdivide.of(0, sequence.length() - 1, 100);
      ScalarTensorFunction suo = BSplineFunctionString.of(2, sequence);
      visualSet.add(domain, domain.map(suo));
    }
    JFreeChart jFreeChart = ListPlot.of(visualSet, true);
    jFreeChart.setBackgroundPaint(Color.WHITE);
    return jFreeChart;
  }

  public static void main(String[] args) throws IOException {
    ChartUtils.saveChartAsPNG(HomeDirectory.Pictures("trap_distr.png"), generate(), 640, 480);
  }
}
