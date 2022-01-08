// code by jph
package ch.alpine.java.awt;

import java.awt.color.ColorSpace;

import ch.alpine.tensor.RationalScalar;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Scalars;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.ext.Integers;
import ch.alpine.tensor.ext.PackageTestAccess;
import ch.alpine.tensor.red.Times;
import ch.alpine.tensor.sca.CubeRoot;
import ch.alpine.tensor.sca.Power;

/** Hint:
 * The xyz values can be obtained via the following {@link ColorSpace}
 * ColorSpace.getInstance(ColorSpace.CS_CIEXYZ)
 * 
 * <p>Reference:
 * https://en.wikipedia.org/wiki/CIELAB_color_space */
public enum Cielab {
  /** Standard Illuminant D65 */
  D65(0.950489, 1, 1.088840), //
  /** Illuminant D50 (used in the printing industry) */
  D50(0.964212, 1, 0.825188), //
  ;

  private static final Scalar DELTA = RationalScalar.of(6, 29);
  private static final Scalar DELTA3 = Power.of(DELTA, 3);
  private static final Scalar FACTOR = Power.of(DELTA, 2).multiply(RealScalar.of(3));
  private static final Scalar OFFSET = RationalScalar.of(4, 29);
  private static final Scalar _016 = RealScalar.of(0.16);
  private static final Scalar _116 = RealScalar.of(1.16);
  private static final Scalar _500 = RealScalar.of(5.00);
  private static final Scalar _200 = RealScalar.of(2.00);
  // ---
  private final Tensor xyzn;

  private Cielab(double... xyzn) {
    this.xyzn = Tensors.vectorDouble(xyzn);
  }

  /** @param xyz vector of length 3 with entries in unit interval
   * @return vector of length 3 with entries in [-1, 1] */
  public Tensor xyz2lab(Tensor xyz) {
    Integers.requireEquals(xyz.length(), 3);
    Tensor fs3 = Times.of(xyz, xyzn.map(Scalar::reciprocal)).map(Cielab::f);
    Scalar L = _116.multiply(fs3.Get(1)).subtract(_016);
    Scalar a = _500.multiply(fs3.Get(0).subtract(fs3.Get(1)));
    Scalar b = _200.multiply(fs3.Get(1).subtract(fs3.Get(2)));
    return Tensors.of(L, a, b);
  }

  /** @param lab vector of length 3 with entries in [-1, 1]
   * @return vector of length 3 with entries in unit interval */
  public Tensor lab2xyz(Tensor lab) {
    Integers.requireEquals(lab.length(), 3);
    Scalar L = lab.Get(0);
    Scalar a = lab.Get(1);
    Scalar b = lab.Get(2);
    Scalar p = L.add(_016).divide(_116);
    Scalar x = f_inv(p.add(a.divide(_500)));
    Scalar y = f_inv(p);
    Scalar z = f_inv(p.subtract(b.divide(_200)));
    return Times.of(Tensors.of(x, y, z), xyzn);
  }

  @PackageTestAccess
  /* package */ static Scalar f(Scalar t) {
    return Scalars.lessEquals(DELTA3, t) //
        ? CubeRoot.FUNCTION.apply(t)
        : t.divide(FACTOR).add(OFFSET);
  }

  @PackageTestAccess
  /* package */ static Scalar f_inv(Scalar t) {
    return Scalars.lessEquals(DELTA, t) //
        ? t.multiply(t).multiply(t)
        : t.subtract(OFFSET).multiply(FACTOR);
  }
}
