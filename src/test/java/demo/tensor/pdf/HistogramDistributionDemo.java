// code by jph
package demo.tensor.pdf;

import java.awt.Dimension;
import java.io.IOException;

import ch.alpine.bridge.fig.Plot;
import ch.alpine.bridge.fig.Show;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.ext.HomeDirectory;
import ch.alpine.tensor.pdf.InverseCDF;
import ch.alpine.tensor.pdf.RandomVariate;
import ch.alpine.tensor.pdf.UnivariateDistribution;
import ch.alpine.tensor.pdf.c.HistogramDistribution;
import ch.alpine.tensor.pdf.c.NormalDistribution;
import ch.alpine.tensor.sca.Clip;
import ch.alpine.tensor.sca.Clips;

public enum HistogramDistributionDemo {
  ;
  public static void main(String[] args) throws IOException {
    UnivariateDistribution dist = (UnivariateDistribution) NormalDistribution.of(1, 2);
    HistogramDistribution distribution = (HistogramDistribution) //
    HistogramDistribution.of(RandomVariate.of(dist, 2000), RealScalar.of(0.25));
    {
      Clip clip = Clips.interval(-5, 8);
      Show show = new Show();
      show.add(new Plot(distribution::at, clip));
      show.add(new Plot(distribution::p_lessEquals, clip));
      show.add(new Plot(dist::at, clip));
      show.export(HomeDirectory.Pictures("hd.png"), new Dimension(640, 480));
    }
    {
      Clip clip = Clips.unit();
      InverseCDF inv1 = InverseCDF.of(distribution);
      InverseCDF inv2 = InverseCDF.of(dist);
      Show show = new Show();
      show.add(new Plot(inv1::quantile, clip));
      show.add(new Plot(inv2::quantile, clip));
      show.export(HomeDirectory.Pictures("hd_inv.png"), new Dimension(640, 480));
    }
  }
}
