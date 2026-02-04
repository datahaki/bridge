package ch.alpine.bridge.cal;

import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.alg.Dot;
import ch.alpine.tensor.alg.UnitVector;
import ch.alpine.tensor.lie.rot.Cross;
import ch.alpine.tensor.mat.IdentityMatrix;
import ch.alpine.tensor.nrm.Vector2Norm;
import ch.alpine.tensor.sca.N;
import ch.alpine.tensor.sca.pow.Sqrt;
import ch.alpine.tensor.sca.tri.Sinc;

public enum GeoPosition {
  ;
  private static final Tensor ID3 = IdentityMatrix.of(3).map(N.DOUBLE);
  private static final Scalar HALF = RealScalar.of(0.5);

  /** @param vector of length 3
   * @return orthogonal matrix with dimensions 3 x 3 */
  public static Tensor vectorExp(Tensor vector) {
    Scalar beta = Vector2Norm.of(vector);
    Scalar s1 = Sinc.FUNCTION.apply(beta);
    Tensor X1 = Cross.skew3(vector.multiply(s1));
    Scalar h2 = Sinc.FUNCTION.apply(beta.multiply(HALF));
    Scalar r2 = Sqrt.FUNCTION.apply(h2.multiply(h2).multiply(HALF));
    Tensor X2 = Cross.skew3(vector.multiply(r2));
    return ID3.add(X1).add(X2.dot(X2));
  }

  public static Tensor of(Tensor lat_lon) {
    Scalar factor = RealScalar.of(Math.PI / 180);
    Scalar lat = lat_lon.Get(0).multiply(factor);
    Tensor rot1 = vectorExp(Tensors.of(lat.zero(), lat.negate(), lat.zero()));
    Scalar lon = lat_lon.Get(1).multiply(factor);
    Tensor rot2 = vectorExp(Tensors.of(lon.zero(), lon.zero(), lon));
    return Dot.of(rot2, rot1, UnitVector.of(3, 0));
  }
}
