// code by jph
package ch.alpine.bridge.fig.grd;

import java.io.Serializable;

import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.api.ScalarUnaryOperator;
import ch.alpine.tensor.ext.Integers;
import ch.alpine.tensor.qty.QuantityUnit;
import ch.alpine.tensor.qty.UnitConvert;
import ch.alpine.tensor.sca.Clip;

/** affine-linear axis */
public class ConfLin implements ConfBase, Serializable {
  public static ConfBase create(int ofs, int length, Clip clip, boolean incr) {
    Scalar beg = incr ? clip.min() : clip.max();
    Scalar end = incr ? clip.max() : clip.min();
    return new ConfLin(ofs, length, clip, beg, end);
  }

  protected final int ofs;
  protected final int length;
  protected final Clip clip;
  private final Scalar beg;
  private final Scalar end;
  protected final ScalarUnaryOperator unitConvert;
  protected final Scalar model2pixel;
  protected final Scalar pixel2model;

  /** @param ofs start of drawing area
   * @param length greather equals 2
   * @param clip
   * @param beg model value at pixel ofs
   * @param end model value at pixel ofs + length - 1 */
  private ConfLin(int ofs, int length, Clip clip, Scalar beg, Scalar end) {
    Integers.requireLessThan(1, length);
    this.ofs = ofs;
    this.length = length;
    this.clip = clip;
    this.beg = beg;
    this.end = end;
    Scalar sw = RealScalar.of(length - 1);
    unitConvert = UnitConvert.SI().to(QuantityUnit.of(clip));
    model2pixel = sw.divide(end.subtract(beg));
    pixel2model = end.subtract(beg).divide(sw);
  }

  @Override
  public int length() {
    return length;
  }

  @Override
  public Clip clip() {
    return clip;
  }

  @Override
  public double pixel(Scalar value) {
    Scalar val = unitConvert.apply(value);
    return ofs + val.subtract(beg).multiply(model2pixel).number().doubleValue();
  }

  @Override
  public Scalar model(int pixel) {
    return beg.add(RealScalar.of(pixel - ofs).multiply(pixel2model));
  }

  @Override
  public ConfBase pruned() {
    return new ConfLin(0, length, clip, beg, end);
  }

  @Override
  public Scalar shift(int pixel0, int pixel1) {
    return RealScalar.of(pixel0 - pixel1).multiply(pixel2model);
  }
}
