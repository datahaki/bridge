// code by jph
package ch.alpine.bridge.fig.grd;

import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.api.ScalarUnaryOperator;
import ch.alpine.tensor.ext.Integers;
import ch.alpine.tensor.io.MathematicaFormat;
import ch.alpine.tensor.sca.Clip;
import ch.alpine.tensor.sca.Clips;
import ch.alpine.tensor.sca.Sign;
import ch.alpine.tensor.sca.exp.Exp;
import ch.alpine.tensor.sca.exp.Log;

/** used for geo component mercator projection */
public class ConfLogDecr implements ConfBase {
  public static ConfLogDecr exp(int ofs, int length, Clip clip) {
    Scalar min = Log.FUNCTION.apply(clip.min());
    Scalar max = Log.FUNCTION.apply(clip.max());
    Clip log_clip = Clips.interval(min, max);
    return new ConfLogDecr(ofs + length - 1, length, log_clip);
  }

  private final int ofs;
  private final int length;
  private final Clip clip;
  private final Scalar model2pixel;
  private final Scalar pixel2model;

  private ConfLogDecr(int ofs, int length, Clip clip) {
    this.ofs = ofs;
    this.length = length;
    this.clip = clip;
    Integers.requireLessThan(1, length);
    Scalar sw = RealScalar.of(length - 1);
    ScalarUnaryOperator suo = Scalar::negate;
    model2pixel = suo.apply(sw.divide(clip.length()));
    pixel2model = suo.apply(clip.length().divide(sw));
    Sign.requirePositive(model2pixel.negate());
    Sign.requirePositive(pixel2model.negate());
  }

  @Override
  public double pixel(Scalar x) {
    return ofs + Log.FUNCTION.apply(x).subtract(clip.min()).multiply(model2pixel).number().doubleValue();
  }

  @Override
  public Scalar model(int pixel) {
    return Exp.FUNCTION.apply(clip.min().add(RealScalar.of(pixel - ofs).multiply(pixel2model)));
  }

  @Override
  public ConfBase pruned() {
    return new ConfLogDecr(0, length, clip);
  }

  @Override
  public Scalar shift(int pixel0, int pixel1) {
    throw new UnsupportedOperationException();
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
  public String toString() {
    return MathematicaFormat.concise("ConfLogDecr", ofs, length, clip);
  }
}
