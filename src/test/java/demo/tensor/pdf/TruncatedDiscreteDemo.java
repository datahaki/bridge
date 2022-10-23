// code by jph
package demo.tensor.pdf;

import java.io.IOException;

import ch.alpine.bridge.fig.Histogram;
import ch.alpine.bridge.fig.ListPlot;
import ch.alpine.bridge.fig.Show;
import ch.alpine.bridge.fig.Showable;
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
  public static Showable generate() {
    Distribution original = PoissonDistribution.of(7);
    Distribution distribution = TruncatedDistribution.of(original, Clips.interval(5, 10));
    PDF pdf = PDF.of(distribution);
    CDF cdf = CDF.of(distribution);
    PDF pdf_o = PDF.of(original);
    Show visualSet = new Show();
    {
      Tensor domain = Range.of(0, 12);
      visualSet.add(new ListPlot(domain, domain.map(pdf::at)));
      visualSet.add(new ListPlot(domain, domain.map(cdf::p_lessEquals)));
      visualSet.add(new ListPlot(domain, domain.map(pdf_o::at)));
    }
    return Histogram.of(visualSet);
  }

  public static void main(String[] args) throws IOException {
    // show.export(HomeDirectory.Pictures( //
    // TruncatedDiscreteDemo.class.getSimpleName() + ".png"), generate(), //
    // new Dimension(640, 480));
  }
}
