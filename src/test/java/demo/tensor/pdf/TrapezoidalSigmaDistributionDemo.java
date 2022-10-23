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
import ch.alpine.tensor.pdf.c.TrapezoidalDistribution;
import ch.alpine.tensor.sca.Clip;
import ch.alpine.tensor.sca.Clips;

public enum TrapezoidalSigmaDistributionDemo {
  ;
  public static void main(String[] args) throws IOException {
    Distribution dist1 = NormalDistribution.of(2, 1.2);
    Distribution dist2 = TrapezoidalDistribution.with(2, 1.2, 2.4);
    {
      Clip clip = Clips.interval(-3 + 2, 3 + 2);
      Show show = new Show();
      PDF pdf1 = PDF.of(dist1);
      PDF pdf2 = PDF.of(dist2);
      show.add(new Plot(pdf1::at, clip));
      show.add(new Plot(pdf2::at, clip));
      show.export(HomeDirectory.Pictures("trapzsigma.png"), new Dimension(640, 480));
    }
  }
}
