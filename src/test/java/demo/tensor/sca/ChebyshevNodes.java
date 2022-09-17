// code by jph
package demo.tensor.sca;

import ch.alpine.tensor.RationalScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.alg.Transpose;
import ch.alpine.tensor.alg.UnitVector;
import ch.alpine.tensor.api.ScalarUnaryOperator;
import ch.alpine.tensor.num.Pi;
import ch.alpine.tensor.sca.ply.ClenshawChebyshev;
import ch.alpine.tensor.sca.tri.Cos;

public enum ChebyshevNodes {
  ;
  public static Scalar of(int n, int k) {
    if (0 <= k && k < n)
      return Cos.FUNCTION.apply(RationalScalar.of(k + k + 1, n + n).multiply(Pi.VALUE));
    throw new IllegalArgumentException();
  }

  public static Tensor of(int n) {
    return Tensors.vector(k -> of(n, k), n);
  }

  public static Tensor matrix(int n) {
    Tensor nodes = ChebyshevNodes.of(n);
    Tensor matrix = Tensors.empty();
    for (int k = 0; k < n; ++k) {
      ScalarUnaryOperator suo = ClenshawChebyshev.of(UnitVector.of(k + 1, k));
      matrix.append(nodes.map(suo));
    }
    return Transpose.of(matrix);
  }
}
