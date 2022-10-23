// code by jph
package demo.tensor.pdf;

import java.awt.Dimension;
import java.io.IOException;

import ch.alpine.bridge.fig.ListPlot;
import ch.alpine.bridge.fig.Show;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.alg.Subdivide;
import ch.alpine.tensor.ext.HomeDirectory;
import ch.alpine.tensor.pdf.InverseCDF;
import ch.alpine.tensor.pdf.RandomVariate;
import ch.alpine.tensor.pdf.c.EqualizingDistribution;
import ch.alpine.tensor.pdf.c.UniformDistribution;
import ch.alpine.tensor.pdf.d.CategoricalDistribution;

public enum EqualizingDistributionDemo {
  ;
  public static void main(String[] args) throws IOException {
    Tensor unscaledPDF = RandomVariate.of(UniformDistribution.unit(), 20);
    CategoricalDistribution dist1 = CategoricalDistribution.fromUnscaledPDF(unscaledPDF);
    EqualizingDistribution dist2 = (EqualizingDistribution) EqualizingDistribution.fromUnscaledPDF(unscaledPDF);
    {
      Tensor domain = Subdivide.of(0, 20, 20 * 10);
      Show show = new Show();
      show.add(new ListPlot(domain, domain.map(dist1::at)));
      // visualSet.add(domain, domain.map(dist1::p_lessEquals));
      show.add(new ListPlot(domain, domain.map(dist2::at)));
      // Showable jFreeChart = ListPlot.of(show.setJoined(true));
      show.export(HomeDirectory.Pictures("ed.png"), new Dimension(640, 480));
    }
    {
      Tensor domain = Subdivide.of(0, 1, 300);
      InverseCDF inv1 = InverseCDF.of(dist1);
      InverseCDF inv2 = InverseCDF.of(dist2);
      Show show = new Show();
      show.add(new ListPlot(domain, domain.map(inv1::quantile)));
      show.add(new ListPlot(domain, domain.map(inv2::quantile)));
      // Showable jFreeChart = ListPlot.of(show.setJoined(true));
      show.export(HomeDirectory.Pictures("ed_inv.png"), new Dimension(640, 480));
    }
  }
}
