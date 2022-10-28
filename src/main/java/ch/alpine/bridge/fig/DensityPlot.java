// code by jph
package ch.alpine.bridge.fig;

import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.alg.Subdivide;
import ch.alpine.tensor.api.ScalarBinaryOperator;
import ch.alpine.tensor.img.ColorDataGradients;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/DensityPlot.html">DensityPlot</a> */
public enum DensityPlot {
  ;
  private static final int RESOLUTION = 25;

  public static Showable of(ScalarBinaryOperator sbo, CoordinateBoundingBox cbb) {
    Tensor dx = Subdivide.increasing(cbb.getClip(0), RESOLUTION);
    Tensor dy = Subdivide.decreasing(cbb.getClip(1), RESOLUTION);
    Tensor matrix = Tensor.of(dy.stream().parallel() //
        .map(Scalar.class::cast) //
        .map(y -> Tensor.of(dx.stream().map(Scalar.class::cast).map(x -> sbo.apply(x, y)))));
    return ArrayPlot.of(matrix, cbb, ColorDataGradients.DENSITY, true);
  }
}
