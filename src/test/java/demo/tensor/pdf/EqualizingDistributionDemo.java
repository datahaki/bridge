// code by jph
package demo.tensor.pdf;

import java.awt.Dimension;
import java.io.IOException;

import ch.alpine.bridge.fig.ListPlot;
import ch.alpine.bridge.fig.Plot;
import ch.alpine.bridge.fig.Show;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.alg.Range;
import ch.alpine.tensor.ext.HomeDirectory;
import ch.alpine.tensor.pdf.InverseCDF;
import ch.alpine.tensor.pdf.RandomVariate;
import ch.alpine.tensor.pdf.c.EqualizingDistribution;
import ch.alpine.tensor.pdf.c.UniformDistribution;
import ch.alpine.tensor.pdf.d.CategoricalDistribution;
import ch.alpine.tensor.sca.Clip;
import ch.alpine.tensor.sca.Clips;

public enum EqualizingDistributionDemo {
  ;
  public static void main(String[] args) throws IOException {
    Tensor unscaledPDF = RandomVariate.of(UniformDistribution.unit(), 20);
    CategoricalDistribution dist1 = CategoricalDistribution.fromUnscaledPDF(unscaledPDF);
    EqualizingDistribution dist2 = (EqualizingDistribution) EqualizingDistribution.fromUnscaledPDF(unscaledPDF);
    {
      Clip clip = Clips.positive(20);
      Show show = new Show();
      show.add(ListPlot.of(dist1::at, Range.of(0, 20)));
      // visualSet.add(domain, domain.map(dist1::p_lessEquals));
      show.add(Plot.of(dist2::at, clip));
      show.export(HomeDirectory.Pictures("ed.png"), new Dimension(640, 480));
    }
    {
      Clip clip = Clips.unit();
      InverseCDF inv1 = InverseCDF.of(dist1);
      InverseCDF inv2 = InverseCDF.of(dist2);
      Show show = new Show();
      show.add(Plot.of(inv1::quantile, clip));
      show.add(Plot.of(inv2::quantile, clip));
      show.export(HomeDirectory.Pictures("ed_inv.png"), new Dimension(640, 480));
    }
  }
}
