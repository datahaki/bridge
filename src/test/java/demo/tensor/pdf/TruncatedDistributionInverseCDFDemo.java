// code by jph
package demo.tensor.pdf;

import java.awt.Dimension;
import java.io.IOException;

import ch.alpine.bridge.fig.Showable;
import ch.alpine.bridge.fig.ListPlot;
import ch.alpine.bridge.fig.Show;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.alg.Subdivide;
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
    Show visualSet = new Show();
    {
      Tensor domain = Subdivide.of(0, 1, 100);
      visualSet.add(domain, domain.map(inverseCDF::quantile));
    }
    Showable jFreeChart = ListPlot.of(visualSet);
    Show.export(HomeDirectory.Pictures("truncated_inversecdf.png"), jFreeChart, new Dimension(640, 480));
  }
}
