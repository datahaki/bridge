// code by jph
package ch.alpine.bridge.gfx;

import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.Unprotect;
import ch.alpine.tensor.sca.tri.Cos;
import ch.alpine.tensor.sca.tri.Sin;

enum Se2Matrix {
  ;
  /** maps a vector from the group SE2 to a matrix in SE2
   * 
   * @param xya = {px, py, angle}
   * @return matrix with dimensions 3x3
   * <pre>
   * [+Ca -Sa px]
   * [+Sa +Ca py]
   * [0 0 1]
   * </pre>
   * @throws Exception if parameter g is not a vector of length 3 */
  public static Tensor of(Tensor xya) {
    Scalar x = xya.Get(0);
    Scalar y = xya.Get(1);
    Scalar zx = Unprotect.zero_negateUnit(x);
    Scalar zy = Unprotect.zero_negateUnit(y);
    Scalar angle = xya.Get(2);
    Scalar cos = Cos.FUNCTION.apply(angle);
    Scalar sin = Sin.FUNCTION.apply(angle);
    return Tensors.matrix(new Scalar[][] { //
        { cos, sin.negate(), x }, //
        { sin, cos /*----*/, y }, //
        { zx, zy, RealScalar.ONE }, //
    });
  }

  /** @param xy of the form {px, py, ...}
   * @return
   * <pre>
   * [1 0 px]
   * [0 1 py]
   * [0 0 1]
   * </pre> */
  public static Tensor translation(Tensor xy) {
    Scalar x = xy.Get(0);
    Scalar y = xy.Get(1);
    Scalar zx = Unprotect.zero_negateUnit(x);
    Scalar zy = Unprotect.zero_negateUnit(y);
    return Tensors.matrix(new Scalar[][] { //
        { RealScalar.ONE, RealScalar.ZERO, x }, //
        { RealScalar.ZERO, RealScalar.ONE, y }, //
        { zx, zy, RealScalar.ONE }, //
    });
  }
}
