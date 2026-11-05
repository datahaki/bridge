// code by jph
package ch.alpine.bridge.fig;

import java.io.Serializable;

import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.api.ScalarUnaryOperator;
import ch.alpine.tensor.ext.Integers;
import ch.alpine.tensor.qty.QuantityUnit;
import ch.alpine.tensor.qty.UnitConvert;
import ch.alpine.tensor.sca.Clip;

/** affine-linear axis */
abstract class ConfLin implements ConfBase, Serializable {
  private final int ofs;
  private final int length;
  private final Clip clip;
  private final ScalarUnaryOperator unitConvert;
  private final Scalar model2pixel;
  private final Scalar pixel2model;

  /** @param ofs start of drawing area
   * @param length
   * @param clip
   * @param suo */
  public ConfLin(int ofs, int length, Clip clip, ScalarUnaryOperator suo) {
    Integers.requireLessThan(1, length);
    this.ofs = ofs;
    this.length = length;
    this.clip = clip;
    Scalar sw = RealScalar.of(length - 1);
    unitConvert = UnitConvert.SI().to(QuantityUnit.of(clip));
    model2pixel = suo.apply(sw.divide(clip.length()));
    pixel2model = suo.apply(clip.length().divide(sw));
  }

  @Override
  public final int length() {
    return length;
  }

  @Override
  public final Clip clip() {
    return clip;
  }

  @Override
  public final double pixel(Scalar x) {
    return ofs + unitConvert.apply(x).subtract(clip.min()).multiply(model2pixel).number().doubleValue();
  }

  @Override
  public final Scalar model(int pixel) {
    return clip.min().add(RealScalar.of(pixel - ofs).multiply(pixel2model));
  }

  @Override
  public final Scalar shift(int pixel0, int pixel1) {
    return RealScalar.of(pixel0 - pixel1).multiply(pixel2model);
  }
}
