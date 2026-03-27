// code by jph
package ch.alpine.bridge.fig;

import java.io.Serializable;

import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.api.ScalarUnaryOperator;
import ch.alpine.tensor.ext.Integers;
import ch.alpine.tensor.sca.Clip;

public abstract class ConfBase implements Serializable {
  private final int ofs;
  private final int length;
  private final Clip clip;
  private final Scalar model2pixel;
  private final Scalar pixel2model;

  public ConfBase(int ofs, int length, Clip clip, ScalarUnaryOperator suo) {
    Integers.requireLessThan(1, length);
    this.ofs = ofs;
    this.length = length;
    this.clip = clip;
    Scalar sw = RealScalar.of(length - 1);
    model2pixel = suo.apply(sw.divide(clip.length()));
    pixel2model = suo.apply(clip.length().divide(sw));
  }

  /** @return this (ConfIncr or ConfDecr) but with ofs == 0 */
  public abstract ConfBase pruned();

  /** @param x
   * @return pixel coordinate of x */
  public final double pixel(Scalar x) {
    return ofs + x.subtract(clip.min()).multiply(model2pixel).number().doubleValue();
  }

  public final Scalar model(int pixel) {
    return clip.min().add(RealScalar.of(pixel - ofs).multiply(pixel2model));
  }

  public final Scalar pixel2model() {
    return pixel2model;
  }

  public int length() {
    return length;
  }

  public final Clip clip() {
    return clip;
  }
}
