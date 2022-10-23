// code by jph
package demo.tensor.pdf;

import java.io.IOException;

import ch.alpine.bridge.fig.ListPlot;
import ch.alpine.bridge.fig.Show;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.alg.Subdivide;
import ch.alpine.tensor.api.ScalarTensorFunction;
import ch.alpine.tensor.itp.BSplineFunctionString;
import ch.alpine.tensor.pdf.CDF;
import ch.alpine.tensor.pdf.Distribution;
import ch.alpine.tensor.pdf.PDF;
import ch.alpine.tensor.pdf.c.TrapezoidalDistribution;

public enum TrapezoidalDistributionDemo {
  ;
  public static Show generate() {
    Distribution distribution = TrapezoidalDistribution.of(0.5, 1.5, 1.5, 2.5);
    PDF pdf = PDF.of(distribution);
    CDF cdf = CDF.of(distribution);
    Show show = new Show();
    {
      Tensor domain = Subdivide.of(0, 4, 100);
      show.add(new ListPlot(domain, domain.map(pdf::at)));
      show.add(new ListPlot(domain, domain.map(cdf::p_lessEquals)));
    }
    {
      Tensor sequence = Tensors.vector(0, 0, 1, 1);
      Tensor domain = Subdivide.of(0, sequence.length() - 1, 100);
      ScalarTensorFunction suo = BSplineFunctionString.of(2, sequence);
      show.add(new ListPlot(domain, domain.map(suo)));
    }
    // Showable jFreeChart = ListPlot.of(show);
    return show;
  }

  public static void main(String[] args) throws IOException {
    // Show.export(HomeDirectory.Pictures("trap_distr.png"), generate(), //
    // new Dimension(640, 480));
  }
}
