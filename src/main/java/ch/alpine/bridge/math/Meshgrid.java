// code by jph
package ch.alpine.bridge.math;

import java.awt.Dimension;
import java.io.Serializable;
import java.util.List;

import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Slash;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Unprotect;
import ch.alpine.tensor.alg.Subdivide;
import ch.alpine.tensor.api.ScalarBinaryOperator;
import ch.alpine.tensor.api.TensorUnaryOperator;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;
import ch.alpine.tensor.sca.N;

/** the name is inspired by MATLAB::meshgrid
 * 
 * @param cbb
 * @param width
 * @param height */
public record Meshgrid(CoordinateBoundingBox cbb, int width, int height) implements Serializable {
  public static Meshgrid of(CoordinateBoundingBox cbb, int res) {
    return new Meshgrid(cbb, res, res);
  }

  public static Meshgrid of(CoordinateBoundingBox cbb, Dimension dimension) {
    return new Meshgrid(cbb, dimension.width, dimension.height);
  }

  /** the function is called "image" because of the ordering of the return values:
   * dim 0 has cbb.clip(1) decreasing
   * dim 1 has cbb.clip(0) increasing
   * 
   * @param tuo that maps a vector {x, y} to a tensor
   * @return */
  public Tensor image(TensorUnaryOperator tuo) {
    Tensor px = px_incr();
    Tensor py = py_decr();
    return Tensor.of(py.stream() //
        .parallel() //
        .map(y -> Slash.of(px, x -> tuo.apply(Unprotect.using(List.of(x, y))))), py.length());
  }

  public Tensor image(ScalarBinaryOperator sbo) {
    Tensor px = px_incr();
    Tensor py = py_decr();
    return Tensor.of(py.stream() //
        .map(Scalar.class::cast) //
        .parallel() //
        .map(y -> Slash.vector(px, x -> sbo.apply(x, y))), py.length());
  }

  /** @return vector */
  public Tensor px_incr() {
    return Subdivide.intermediate_increasing(cbb.clip(0), width).maps(N.DOUBLE);
  }

  /** @return vector */
  public Tensor py_decr() {
    return Subdivide.intermediate_decreasing(cbb.clip(1), height).maps(N.DOUBLE);
  }
}
