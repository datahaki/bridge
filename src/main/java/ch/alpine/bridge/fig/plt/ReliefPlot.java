// code by jph
package ch.alpine.bridge.fig.plt;

import ch.alpine.bridge.fig.BarLegend;
import ch.alpine.bridge.fig.Showable;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.api.ScalarTensorFunction;
import ch.alpine.tensor.img.ImageResize;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;

/** <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/ReliefPlot.html">ReliefPlot</a> */
public enum ReliefPlot {
  ;
  public static Showable of(Tensor matrix, CoordinateBoundingBox cbb, ScalarTensorFunction colorDataGradient) {
    ReliefImage reliefImage = ReliefImage.of(matrix, cbb, colorDataGradient);
    BarLegend barLegend = new BarLegend(reliefImage.clip(), colorDataGradient);
    return ImagePlot.of(reliefImage.bufferedImage(), ImageResize.DEGREE_3, cbb, barLegend, false, RealScalar.ONE);
  }
}
