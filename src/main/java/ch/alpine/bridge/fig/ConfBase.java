// code by jph
package ch.alpine.bridge.fig;

import java.io.Serializable;

import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.api.ScalarUnaryOperator;
import ch.alpine.tensor.sca.Clip;

public abstract class ConfBase implements Serializable {
  final int ofs;
  final int width;
  final Clip clip;
  final Scalar model2pixel;
  final Scalar pixel2model;

  public ConfBase(int ofs, int width, Clip clip, ScalarUnaryOperator suo) {
    this.ofs = ofs;
    this.width = width;
    this.clip = clip;
    Scalar sw = RealScalar.of(width - 1);
    model2pixel = suo.apply(sw.divide(clip.width()));
    pixel2model = suo.apply(clip.width().divide(sw));
  }

  public abstract ConfBase clipped();

  public final double pixel(Scalar x) {
    return ofs + x.subtract(clip.min()).multiply(model2pixel).number().doubleValue();
  }

  public final Scalar model(int pixel) {
    return clip.min().add(RealScalar.of(pixel - ofs).multiply(pixel2model));
  }

  public final Scalar pixel2model() {
    return pixel2model;
  }

  public final Clip clip() {
    return clip;
  }
}
