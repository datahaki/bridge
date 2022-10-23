// code by jph
package demo.tensor.pdf;

import java.awt.Dimension;
import java.io.IOException;

import ch.alpine.bridge.fig.ListPlot;
import ch.alpine.bridge.fig.Show;
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
      Show show = new Show();
      PDF pdf1 = PDF.of(dist1);
      PDF pdf2 = PDF.of(dist2);
      show.add(new ListPlot(domain, domain.map(pdf1::at)));
      show.add(new ListPlot(domain, domain.map(pdf2::at)));
      // Showable jFreeChart = ListPlot.of(show);
      show.export(HomeDirectory.Pictures("triangular.png"), new Dimension(640, 480));
    }
  }
}
