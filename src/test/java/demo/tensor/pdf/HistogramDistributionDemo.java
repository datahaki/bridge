// code by jph
package demo.tensor.pdf;

import java.awt.Dimension;
import java.io.IOException;

import ch.alpine.bridge.fig.Showable;
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
      Show visualSet = new Show();
      visualSet.add(domain, domain.map(distribution::at));
      visualSet.add(domain, domain.map(distribution::p_lessEquals));
      visualSet.add(domain, domain.map(dist::at));
      Showable jFreeChart = ListPlot.of(visualSet);
      Show.export(HomeDirectory.Pictures("hd.png"), jFreeChart, //
          new Dimension(640, 480));
    }
    {
      Tensor domain = Subdivide.of(0, 1, 300);
      InverseCDF inv1 = InverseCDF.of(distribution);
      InverseCDF inv2 = InverseCDF.of(dist);
      Show visualSet = new Show();
      visualSet.add(domain, domain.map(inv1::quantile));
      visualSet.add(domain, domain.map(inv2::quantile));
      Showable jFreeChart = ListPlot.of(visualSet);
      Show.export(HomeDirectory.Pictures("hd_inv.png"), jFreeChart, //
          new Dimension(640, 480));
    }
  }
}
