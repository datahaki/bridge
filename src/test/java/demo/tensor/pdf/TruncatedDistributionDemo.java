// code by jph
package demo.tensor.pdf;

import java.awt.Dimension;
import java.io.IOException;

import ch.alpine.bridge.fig.ListPlot;
import ch.alpine.bridge.fig.Show;
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
    Show show = new Show();
    {
      Tensor domain = Subdivide.of(-3, 3, 100);
      show.add(new ListPlot(domain, domain.map(pdf::at)));
      show.add(new ListPlot(domain, domain.map(cdf::p_lessEquals)));
      show.add(new ListPlot(domain, domain.map(pdf_o::at)));
    }
    show.export(HomeDirectory.Pictures("truncated.png"), new Dimension(640, 480));
  }
}
