// code by jph
package ch.alpine.bridge.fig;

import java.util.List;

import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Unprotect;
import ch.alpine.tensor.alg.Subdivide;
import ch.alpine.tensor.api.ScalarBinaryOperator;
import ch.alpine.tensor.api.TensorUnaryOperator;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;
import ch.alpine.tensor.sca.N;

/** @param cbb
 * @param res */
public record Meshgrid(CoordinateBoundingBox cbb, int res0, int res1) {
  public static Meshgrid of(CoordinateBoundingBox cbb, int res) {
    return new Meshgrid(cbb, res, res);
  }

  /** @param tuo that maps a vector {x, y} to a tensor
   * @return */
  public Tensor image(TensorUnaryOperator tuo) {
    Tensor dx = Subdivide.intermediate_increasing(cbb.clip(0), res0).maps(N.DOUBLE);
    Tensor dy = Subdivide.intermediate_decreasing(cbb.clip(1), res1).maps(N.DOUBLE);
    return Tensor.of(dy.stream().parallel() //
        .map(y -> Tensor.of(dx.stream() //
            .map(x -> tuo.apply(Unprotect.using(List.of(x, y)))) //
        )));
  }

  public Tensor image(ScalarBinaryOperator sbo) {
    Tensor dx = Subdivide.intermediate_increasing(cbb.clip(0), res0).maps(N.DOUBLE);
    Tensor dy = Subdivide.intermediate_decreasing(cbb.clip(1), res1).maps(N.DOUBLE);
    return Tensor.of(dy.stream().map(Scalar.class::cast).parallel() //
        .map(y -> Tensor.of(dx.stream().map(Scalar.class::cast) //
            .map(x -> sbo.apply(x, y)) //
        )));
  }
}
