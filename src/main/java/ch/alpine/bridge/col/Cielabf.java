// code by jph
package ch.alpine.bridge.col;

import ch.alpine.tensor.RationalScalar;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Scalars;
import ch.alpine.tensor.sca.pow.CubeRoot;
import ch.alpine.tensor.sca.pow.Power;

/** <p>Reference:
 * https://en.wikipedia.org/wiki/CIELAB_color_space */
/* package */ enum Cielabf {
  ;
  private static final Scalar DELTA = RationalScalar.of(6, 29);
  private static final Scalar DELTA3 = Power.of(DELTA, 3);
  private static final Scalar FACTOR = Power.of(DELTA, 2).multiply(RealScalar.of(3));
  private static final Scalar OFFSET = RationalScalar.of(4, 29);

  public static Scalar forward(Scalar t) {
    return Scalars.lessEquals(DELTA3, t) //
        ? CubeRoot.FUNCTION.apply(t)
        : t.divide(FACTOR).add(OFFSET);
  }

  public static Scalar inverse(Scalar t) {
    return Scalars.lessEquals(DELTA, t) //
        ? t.multiply(t).multiply(t)
        : t.subtract(OFFSET).multiply(FACTOR);
  }
}
