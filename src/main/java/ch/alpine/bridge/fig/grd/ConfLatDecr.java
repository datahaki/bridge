// code by jph
package ch.alpine.bridge.fig.grd;

import java.io.Serializable;

import ch.alpine.bridge.geo.TilePixel;
import ch.alpine.bridge.math.Unitless;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.sca.Clip;

/** used for geo component mercator projection */
public class ConfLatDecr implements ConfBase, Serializable {
  public int ofs;
  public int length;
  public Clip clip;
  public TilePixel origin;

  public ConfLatDecr(int ofs, int length, Clip clip, TilePixel origin) {
    this.ofs = ofs;
    this.length = length;
    this.clip = clip;
    this.origin = origin;
  }

  @Override
  public int length() {
    return length;
  }

  @Override
  public Clip domain_clip() {
    return clip;
  }

  @Override
  public Clip view_clip() {
    return clip;
  }

  @Override
  public double pixel(Scalar value) {
    // TODO computation wasteful since only Y component is needed
    return origin.from(Tensors.of(value.zero(), value)) //
        .map(tilePixel -> tilePixel.absY() - origin.absY()) //
        .orElse(0L);
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
