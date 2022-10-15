// code by jph
package demo.tensor.pdf;

import java.awt.Dimension;
import java.io.IOException;

import ch.alpine.bridge.fig.ChartUtils;
import ch.alpine.bridge.fig.JFreeChart;
import ch.alpine.bridge.fig.ListPlot;
import ch.alpine.bridge.fig.VisualSet;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.alg.Subdivide;
import ch.alpine.tensor.ext.HomeDirectory;
import ch.alpine.tensor.pdf.Distribution;
import ch.alpine.tensor.pdf.PDF;
import ch.alpine.tensor.pdf.c.NormalDistribution;
import ch.alpine.tensor.pdf.c.TriangularDistribution;

public enum TriangularDistributionDemo {
  ;
  public static void main(String[] args) throws IOException {
    Distribution dist1 = NormalDistribution.of(2, 0.5);
    Distribution dist2 = TriangularDistribution.with(2, 0.5);
    {
      Tensor domain = Subdivide.of(-3 + 2, 3 + 2, 6 * 10);
      VisualSet visualSet = new VisualSet();
      PDF pdf1 = PDF.of(dist1);
      PDF pdf2 = PDF.of(dist2);
      visualSet.add(domain, domain.map(pdf1::at));
      visualSet.add(domain, domain.map(pdf2::at));
      JFreeChart jFreeChart = ListPlot.of(visualSet);
      ChartUtils.saveChartAsPNG(HomeDirectory.Pictures("triangular.png"), jFreeChart, new Dimension(640, 480));
    }
  }
}
