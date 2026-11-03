package ch.alpine.bridge.fig;

import ch.alpine.bridge.geo.TilePixel;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.api.ScalarUnaryOperator;
import ch.alpine.tensor.qty.Degree;
import ch.alpine.tensor.qty.UnitConvert;
import ch.alpine.tensor.qty.UnitSystem;
import ch.alpine.tensor.sca.Clip;

class ConfLatDecr implements ConfIf {
  private static final ScalarUnaryOperator suo = UnitConvert.SI().to(Degree.UNIT);
  private final int ofs;
  private final int length;
  private final Clip clip;
  private final TilePixel origin;

  // TODO origin should be referenced at one of the corners of the rectangle !!!
  public ConfLatDecr(int ofs, int length, Clip clip, TilePixel origin) {
    this.ofs = ofs;
    this.length = length;
    this.clip = clip;
    this.origin = origin;
  }

  @Override
  public Clip clip() {
    return clip;
  }

  @Override
  public int length() {
    return length;
  }

  @Override
  public double pixel(Scalar x) {
    TilePixel tilePixel = origin.from(Tensors.of(RealScalar.ZERO, x).maps(UnitSystem.SI()));
    return (int) (tilePixel.absY() - origin.absY());
  }

  @Override
  public Scalar model(int pixel) {
    return suo.apply(origin.shift(0, pixel).lon_lat().Get(1));
  }

  @Override
  public Scalar shift(int pixel0, int pixel1) {
    return null;
  }

  @Override
  public ConfIf pruned() {
    return new ConfLatDecr(0, length(), clip(), origin); // TODO shift!!!
  }
}
