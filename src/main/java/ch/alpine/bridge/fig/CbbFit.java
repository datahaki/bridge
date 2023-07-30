package ch.alpine.bridge.fig;

import java.util.Optional;

import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.alg.VectorQ;
import ch.alpine.tensor.mat.Tolerance;
import ch.alpine.tensor.sca.Sign;

// TODO BRIDGE API not final
public enum CbbFit {
  ;
  /** max_r r | a*r <= b
   * 
   * @param a
   * @param b for instance {rectangle.width, rectangle.height} available canvas
   * @return */
  public static Optional<Tensor> inside(Tensor a, Tensor b) {
    int n = a.length();
    VectorQ.requireLength(b, n);
    for (int i = 0; i < n; ++i) {
      Scalar r = b.Get(i).divide(a.Get(i));
      if (b.subtract(a.multiply(r)).stream() //
          .map(Scalar.class::cast) //
          .map(Tolerance.CHOP) //
          .allMatch(Sign::isPositiveOrZero))
        return Optional.of(a.multiply(r));
    }
    return Optional.empty();
  }

  /** max_r r | a*r <= b
   * 
   * @param a
   * @param b for instance {rectangle.width, rectangle.height} available canvas
   * @return */
  public static Optional<Tensor> outside(Tensor a, Tensor b) {
    int n = a.length();
    VectorQ.requireLength(b, n);
    for (int i = 0; i < n; ++i) {
      Scalar r = b.Get(i).divide(a.Get(i));
      if (a.multiply(r).subtract(b).stream() //
          .map(Scalar.class::cast) //
          .map(Tolerance.CHOP) //
          .allMatch(Sign::isPositiveOrZero))
        return Optional.of(a.multiply(r));
    }
    return Optional.empty();
  }
}
