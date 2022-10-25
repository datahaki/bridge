// code by jph
package demo.tensor.pdf;

import java.awt.Dimension;
import java.io.IOException;

import ch.alpine.bridge.fig.ListLinePlot;
import ch.alpine.bridge.fig.Show;
import ch.alpine.tensor.RationalScalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.alg.Range;
import ch.alpine.tensor.ext.HomeDirectory;
import ch.alpine.tensor.pdf.CDF;
import ch.alpine.tensor.pdf.Distribution;
import ch.alpine.tensor.pdf.PDF;
import ch.alpine.tensor.pdf.d.BinomialDistribution;

public enum BinomialDistributionDemo {
  ;
  public static void main(String[] args) throws IOException {
    int n = 50;
    Distribution distribution = BinomialDistribution.of(n, RationalScalar.HALF);
    PDF pdf = PDF.of(distribution);
    CDF cdf = CDF.of(distribution);
    Show show = new Show();
    {
      Tensor domain = Range.of(0, n + 1);
      show.add(ListLinePlot.of(domain, domain.map(pdf::at)));
      show.add(ListLinePlot.of(domain, domain.map(cdf::p_lessEquals)));
    }
    show.export(HomeDirectory.Pictures("binomial_distr.png"), new Dimension(640, 480));
  }
}
