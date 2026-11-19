// code by jph
package ch.alpine.bridge.fig.grd;

import java.io.Serializable;

import ch.alpine.bridge.geo.TilePixel;
import ch.alpine.bridge.math.Unitless;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensors;

/** used for geo component mercator projection */
public record AxisMercator(TilePixel origin) implements AxisMap, Serializable {
  @Override
  public Scalar model2range(Scalar value) {
    // TODO computation wasteful since only Y component is needed
    TilePixel tilePixel = origin.from(Tensors.of(value.zero(), value)).orElseThrow(); //
    return RealScalar.of(tilePixel.absY() - origin.absY());
  }

  @Override
  public Scalar range2model(Scalar range) {
    int pixel = range.number().intValue();
    // TODO computation wasteful since only Y component is needed
    return Unitless.DEGREES.unitConvert().apply(origin.shift(0, pixel).lon_lat().Get(1));
  }

  @Override
  public TickOption tickOption() {
    return TickOption.MODEL;
  }
}
