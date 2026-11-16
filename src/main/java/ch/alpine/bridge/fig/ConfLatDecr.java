// code by jph
package ch.alpine.bridge.fig;

import ch.alpine.bridge.geo.TilePixel;
import ch.alpine.bridge.lang.Unitless;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.sca.Clip;

/** used for geo component mercator projection */
record ConfLatDecr(int ofs, int length, Clip clip, TilePixel origin) implements ConfBase {
  @Override
  public double pixel(Scalar x) {
    // TODO computation wasteful since only Y component is needed
    return origin.from(Tensors.of(x.zero(), x)) //
        .map(tilePixel -> tilePixel.absY() - origin.absY()).orElse(0L);
  }

  @Override
  public Scalar model(int pixel) {
    // TODO computation wasteful since only Y component is needed
    return Unitless.DEGREES.unitConvert().apply(origin.shift(0, pixel).lon_lat().Get(1));
  }

  @Override
  public ConfBase pruned() {
    return new ConfLatDecr(0, length, clip, origin.shift(0, ofs));
  }

  @Override
  public Scalar shift(int pixel0, int pixel1) {
    throw new UnsupportedOperationException();
  }
}
