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
  protected final Clip xRange;
  final Scalar x2pixel;
  protected final Scalar pixel2x;

  public ConfBase(int x, int width, Clip xRange, int xBaseline) {
    this.x = x;
    this.xBaseline = xBaseline;
    this.width = width;
    this.xRange = xRange;
    x2pixel = RealScalar.of(width - 1).divide(xRange.width());
    Sign.requirePositive(x2pixel);
    pixel2x = xRange.width().divide(RealScalar.of(width - 1));
  }

  public abstract double x_pos(Scalar x);

  public abstract Scalar value(int point_x);

  public abstract ConfBase clipped();

  public abstract Scalar dx(Scalar dx);

  public final Clip clip() {
    return xRange;
  }

  public final int xBaseline() {
    return xBaseline;
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + " x=" + x + " width=" + width;
  }
}
