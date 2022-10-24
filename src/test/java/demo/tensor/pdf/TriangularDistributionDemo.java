// code by jph
package demo.tensor.pdf;

import java.awt.Dimension;
import java.io.IOException;

import ch.alpine.bridge.fig.Plot;
import ch.alpine.bridge.fig.Show;
import ch.alpine.tensor.ext.HomeDirectory;
import ch.alpine.tensor.pdf.Distribution;
import ch.alpine.tensor.pdf.PDF;
import ch.alpine.tensor.pdf.c.NormalDistribution;
import ch.alpine.tensor.pdf.c.TriangularDistribution;
import ch.alpine.tensor.sca.Clip;
import ch.alpine.tensor.sca.Clips;

public enum TriangularDistributionDemo {
  ;
  public static void main(String[] args) throws IOException {
    Distribution dist1 = NormalDistribution.of(2, 0.5);
    Distribution dist2 = TriangularDistribution.with(2, 0.5);
    {
      Clip clip = Clips.interval(-3 + 2, 3 + 2);
      // Tensor domain = Subdivide.of(-3 + 2, 3 + 2, 6 * 10);
      Show show = new Show();
      PDF pdf1 = PDF.of(dist1);
      PDF pdf2 = PDF.of(dist2);
      show.add(Plot.of(pdf1::at, clip));
      show.add(Plot.of(pdf2::at, clip));
      // Showable jFreeChart = ListPlot.of(show);
      show.export(HomeDirectory.Pictures("triangular.png"), new Dimension(640, 480));
    }
  }
}
