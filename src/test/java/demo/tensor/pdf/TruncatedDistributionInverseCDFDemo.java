// code by jph
package demo.tensor.pdf;

import java.awt.Dimension;
import java.io.IOException;

import ch.alpine.bridge.fig.Plot;
import ch.alpine.bridge.fig.Show;
import ch.alpine.tensor.ext.HomeDirectory;
import ch.alpine.tensor.pdf.Distribution;
import ch.alpine.tensor.pdf.InverseCDF;
import ch.alpine.tensor.pdf.TruncatedDistribution;
import ch.alpine.tensor.pdf.c.NormalDistribution;
import ch.alpine.tensor.sca.Clips;

public enum TruncatedDistributionInverseCDFDemo {
  ;
  public static void main(String[] args) throws IOException {
    Distribution original = NormalDistribution.standard();
    Distribution distribution = TruncatedDistribution.of(original, Clips.interval(-1, 2.5));
    InverseCDF inverseCDF = InverseCDF.of(distribution);
    Show show = new Show();
    show.add(Plot.of(inverseCDF::quantile, Clips.unit()));
    show.export(HomeDirectory.Pictures("truncated_inversecdf.png"), new Dimension(640, 480));
  }
}
