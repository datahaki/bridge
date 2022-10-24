// code by jph
package demo.tensor.pdf;

import java.awt.Dimension;
import java.io.IOException;

import ch.alpine.bridge.fig.Plot;
import ch.alpine.bridge.fig.Show;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.api.ScalarTensorFunction;
import ch.alpine.tensor.api.ScalarUnaryOperator;
import ch.alpine.tensor.ext.HomeDirectory;
import ch.alpine.tensor.img.ColorDataLists;
import ch.alpine.tensor.itp.BSplineFunctionString;
import ch.alpine.tensor.pdf.CDF;
import ch.alpine.tensor.pdf.Distribution;
import ch.alpine.tensor.pdf.PDF;
import ch.alpine.tensor.pdf.c.TrapezoidalDistribution;
import ch.alpine.tensor.sca.Clip;
import ch.alpine.tensor.sca.Clips;

public enum TrapezoidalDistributionDemo {
  ;
  public static Show generate() {
    Distribution distribution = TrapezoidalDistribution.of(0.5, 1.5, 1.5, 2.5);
    PDF pdf = PDF.of(distribution);
    CDF cdf = CDF.of(distribution);
    Show show = new Show(ColorDataLists._097.strict().deriveWithAlpha(192));
    {
      Clip clip = Clips.interval(0, 4);
      show.add(Plot.of(pdf::at, clip));
      show.add(Plot.of(cdf::p_lessEquals, clip));
    }
    {
      Tensor sequence = Tensors.vector(0, 0, 1, 1);
      // Tensor domain = Subdivide.of(0, sequence.length() - 1, 100);
      ScalarTensorFunction sto = BSplineFunctionString.of(2, sequence);
      ScalarUnaryOperator suo = s -> (Scalar) sto.apply(s);
      show.add(Plot.of(suo, Clips.interval(0, 3)));
    }
    return show;
  }

  public static void main(String[] args) throws IOException {
    Show show = generate();
    show.export(HomeDirectory.Pictures("trap_distr.png"), new Dimension(640, 480));
  }
}
