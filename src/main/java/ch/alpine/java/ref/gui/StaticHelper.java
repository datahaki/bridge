// code by jph
package ch.alpine.java.ref.gui;

import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.alg.Subdivide;
import ch.alpine.tensor.red.ArgMin;
import ch.alpine.tensor.sca.Abs;

/* package */ enum StaticHelper {
  ;
  public static Scalar distance(Tensor p, Tensor q) {
    return p.subtract(q).flatten(-1) //
        .map(Scalar.class::cast) //
        .map(Abs.FUNCTION) //
        .reduce(Scalar::add).orElseThrow();
  }

  public static Tensor closest(Tensor tensor, Tensor value, int increment) {
    Tensor distance = Tensor.of(tensor.stream().map(row -> distance(row, value)));
    return tensor.get(Math.min(Math.max(0, ArgMin.of(distance) + increment), tensor.length() - 1));
  }

  /***************************************************/
  public static void main(String[] args) {
    Tensor tensor = closest(Subdivide.of(1, 10, 9), RealScalar.of(3), 1);
    System.out.println(tensor);
  }
}
