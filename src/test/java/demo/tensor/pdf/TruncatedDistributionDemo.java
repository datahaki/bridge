// code by jph
package demo.tensor.pdf;

import java.awt.Dimension;
import java.io.IOException;

import ch.alpine.bridge.fig.Plot;
import ch.alpine.bridge.fig.Show;
import ch.alpine.tensor.ext.HomeDirectory;
import ch.alpine.tensor.pdf.CDF;
import ch.alpine.tensor.pdf.Distribution;
import ch.alpine.tensor.pdf.PDF;
import ch.alpine.tensor.pdf.TruncatedDistribution;
import ch.alpine.tensor.pdf.c.NormalDistribution;
import ch.alpine.tensor.sca.Clip;
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
      Clip clip = Clips.interval(-3, 3);
      show.add(new Plot(pdf::at, clip));
      show.add(new Plot(cdf::p_lessEquals, clip));
      show.add(new Plot(pdf_o::at, clip));
    }
    show.export(HomeDirectory.Pictures("truncated.png"), new Dimension(640, 480));
  }
}
