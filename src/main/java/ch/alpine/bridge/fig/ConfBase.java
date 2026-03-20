// code by jph
package ch.alpine.bridge.fig;

import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.sca.Clip;
import ch.alpine.tensor.sca.Sign;

public abstract class ConfBase {
  final int x;
  final int xBaseline;
  final int width;
  final Clip clip;
  final Scalar model2pixel;
  final Scalar pixel2model;

  public ConfBase(int x, int width, Clip xRange, int xBaseline) {
    this.x = x;
    this.xBaseline = xBaseline;
    this.width = width;
    this.clip = xRange;
    model2pixel = RealScalar.of(width - 1).divide(xRange.width());
    Sign.requirePositive(model2pixel);
    pixel2model = xRange.width().divide(RealScalar.of(width - 1));
  }

  public abstract double pixel(Scalar x);

  public abstract Scalar model(int pixel);

  public abstract ConfBase clipped();

  public abstract Scalar dx(Scalar dx);

  public final Clip clip() {
    return clip;
  }

  public final int xBaseline() {
    return xBaseline;
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + " x=" + x + " width=" + width;
  }
}
