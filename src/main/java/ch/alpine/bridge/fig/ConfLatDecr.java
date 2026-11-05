// code by jph
package ch.alpine.bridge.fig;

import ch.alpine.bridge.geo.TilePixel;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.api.ScalarUnaryOperator;
import ch.alpine.tensor.qty.Degree;
import ch.alpine.tensor.qty.UnitConvert;
import ch.alpine.tensor.sca.Clip;

/** used for geo component mercator projection */
record ConfLatDecr(int ofs, int length, Clip clip, TilePixel origin) implements ConfBase {
  private static final ScalarUnaryOperator TO_DEGREE = UnitConvert.SI().to(Degree.UNIT);

  public ConfLatDecr(int ofs, int length, Clip clip, TilePixel origin) {
    this.ofs = ofs;
    this.length = length;
    this.clip = clip;
    this.origin = origin;
  }

  @Override
  public double pixel(Scalar x) {
    // TODO computation wasteful since only Y component is needed
    TilePixel tilePixel = origin.from(Tensors.of(x.zero(), x));
    return (int) (tilePixel.absY() - origin.absY());
  }

  @Override
  public Scalar model(int pixel) {
    // TODO computation wasteful since only Y component is needed
    return TO_DEGREE.apply(origin.shift(0, pixel).lon_lat().Get(1));
  }

  @Override
  public ConfBase pruned() {
    return new ConfLatDecr(0, length, clip, origin.shift(0, ofs));
  }

  @Override
  public Scalar shift(int pixel0, int pixel1) {
    // TODO
    return null;
  }
}
