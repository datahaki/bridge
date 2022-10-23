// code by jph
package demo.tensor.pdf;

import java.awt.Dimension;
import java.io.IOException;

import ch.alpine.bridge.fig.ListPlot;
import ch.alpine.bridge.fig.Show;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.alg.Subdivide;
import ch.alpine.tensor.ext.HomeDirectory;
import ch.alpine.tensor.pdf.InverseCDF;
import ch.alpine.tensor.pdf.RandomVariate;
import ch.alpine.tensor.pdf.UnivariateDistribution;
import ch.alpine.tensor.pdf.c.HistogramDistribution;
import ch.alpine.tensor.pdf.c.NormalDistribution;

public enum HistogramDistributionDemo {
  ;
  public static void main(String[] args) throws IOException {
    UnivariateDistribution dist = (UnivariateDistribution) NormalDistribution.of(1, 2);
    HistogramDistribution distribution = (HistogramDistribution) //
    HistogramDistribution.of(RandomVariate.of(dist, 2000), RealScalar.of(0.25));
    {
      Tensor domain = Subdivide.of(-5, 8, 300);
      Show show = new Show();
      show.add(new ListPlot(domain, domain.map(distribution::at)));
      show.add(new ListPlot(domain, domain.map(distribution::p_lessEquals)));
      show.add(new ListPlot(domain, domain.map(dist::at)));
      show.export(HomeDirectory.Pictures("hd.png"), new Dimension(640, 480));
    }
    {
      Tensor domain = Subdivide.of(0, 1, 300);
      InverseCDF inv1 = InverseCDF.of(distribution);
      InverseCDF inv2 = InverseCDF.of(dist);
      Show show = new Show();
      show.add(new ListPlot(domain, domain.map(inv1::quantile)));
      show.add(new ListPlot(domain, domain.map(inv2::quantile)));
      show.export(HomeDirectory.Pictures("hd_inv.png"), new Dimension(640, 480));
    }
  }
}
