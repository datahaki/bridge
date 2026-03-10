// code by jph
package ch.alpine.bridge.gfx;

import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.alg.Dot;
import ch.alpine.tensor.alg.Transpose;
import ch.alpine.tensor.mat.IdentityMatrix;
import ch.alpine.tensor.red.EqualsReduce;
import ch.alpine.tensor.red.Times;
import ch.alpine.tensor.sca.Sign;

public record PvmBuilder(Tensor p, Tensor v, Tensor m) {
  private static final Tensor ID3 = IdentityMatrix.of(3).unmodifiable();
  private static final Tensor IN3 = Tensors.matrix(new Number[][] { //
      { 1, 0, 0 }, //
      { 0, -1, 0 }, //
      { 0, 0, 1 }, //
  }).unmodifiable();

  public static PvmBuilder rhs() {
    return new PvmBuilder(ID3, IN3, ID3);
  }

  /** @param pix
   * @param piy */
  public PvmBuilder setOffset(int pix, int piy) {
    Tensor p = IdentityMatrix.of(3);
    p.set(RealScalar.of(pix), 0, 2);
    p.set(RealScalar.of(piy), 1, 2);
    return new PvmBuilder(p, v, m);
  }

  /** @param fx for instance 60[m^-1] results in 60 pixels when multiplied by 1[m]
   * @param fy for instance 20[s^-1] results in 20 pixels when multiplied by 1[s] */
  public PvmBuilder setPerPixel(Scalar fx, Scalar fy) {
    Sign.requirePositive(fx);
    Sign.requirePositive(fy);
    Scalar one = EqualsReduce.one(Tensors.of(fx, fy));
    return new PvmBuilder(p, v, Transpose.of(Times.of(Tensors.of(fx, fy, one), ID3)));
  }

  public PvmBuilder setPerPixel(Scalar f) {
    return setPerPixel(f, f);
  }

  public PvmBuilder setPerPixel(Number f) {
    return setPerPixel(RealScalar.of(f));
  }

  public Tensor digest() {
    return Dot.of(p, v, m);
  }
}
