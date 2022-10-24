// code by jph
package demo.tensor.pdf;

import ch.alpine.bridge.fig.ListPlot;
import ch.alpine.bridge.fig.Show;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.alg.Range;
import ch.alpine.tensor.pdf.CDF;
import ch.alpine.tensor.pdf.Distribution;
import ch.alpine.tensor.pdf.PDF;
import ch.alpine.tensor.pdf.TruncatedDistribution;
import ch.alpine.tensor.pdf.d.PoissonDistribution;
import ch.alpine.tensor.sca.Clips;

public enum TruncatedDiscreteDemo {
  ;
  public static Show generate() {
    Distribution original = PoissonDistribution.of(7);
    Distribution distribution = TruncatedDistribution.of(original, Clips.interval(5, 10));
    PDF pdf = PDF.of(distribution);
    CDF cdf = CDF.of(distribution);
    PDF pdf_o = PDF.of(original);
    Show show = new Show();
    {
      Tensor domain = Range.of(0, 12);
      show.add(ListPlot.of(domain, domain.map(pdf::at)));
      show.add(ListPlot.of(domain, domain.map(cdf::p_lessEquals)));
      show.add(ListPlot.of(domain, domain.map(pdf_o::at)));
    }
    return show;
  }

  public static void main(String[] args) {
    // show.export(HomeDirectory.Pictures( //
    // TruncatedDiscreteDemo.class.getSimpleName() + ".png"), generate(), //
    // new Dimension(640, 480));
  }
}
