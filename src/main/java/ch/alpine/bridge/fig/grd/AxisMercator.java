// code by jph
package ch.alpine.bridge.fig.grd;

import java.awt.Point;
import java.awt.Rectangle;
import java.io.Serializable;

import ch.alpine.bridge.fig.tic.Ticks;
import ch.alpine.bridge.fig.tic.TicksDec;
import ch.alpine.bridge.geo.TilePixel;
import ch.alpine.bridge.math.SI;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.api.ScalarUnaryOperator;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;
import ch.alpine.tensor.opt.nd.CoordinateBounds;

/** used for geo component mercator projection */
public record AxisMercator(TilePixel origin) implements AxisMap, Serializable {
  private static final ScalarUnaryOperator TO_DEGREE = SI.DEGREES.unitConvert();

  @Override
  public Scalar model2range(Scalar model) {
    // TODO computation wasteful since only Y component is needed
    // TODO model may be out of model_clip !
    TilePixel tilePixel = origin.from(Tensors.of(model.zero(), model)).orElseThrow(); //
    return RealScalar.of(tilePixel.absY() - origin.absY());
  }

  @Override
  public Scalar range2model(Scalar range) {
    int pixel = range.number().intValue();
    // TODO computation wasteful since only Y component is needed
    return SI.DEGREES.unitConvert(origin.shift(0, pixel).lon_lat().Get(1));
  }

  @Override
  public TickOption tickOption() {
    return TickOption.MODEL;
  }

  @Override
  public String format(Scalar model) {
    return TickToString.formatValue(model);
  }

  @Override
  public Ticks ticks() {
    return TicksDec.DENSE;
  }

  public CoordinateBoundingBox cbb(Rectangle rectangle) {
    Point p00 = rectangle.getLocation();
    Point p11 = new Point(rectangle.x + rectangle.width, rectangle.y + rectangle.height);
    TilePixel tp00 = origin.shift(p00.x, p00.y);
    TilePixel tp11 = origin.shift(p11.x, p11.y);
    Tensor lon_lat0 = tp00.lon_lat();
    Tensor lon_lat1 = tp11.lon_lat();
    return CoordinateBounds.of(Tensors.of(lon_lat0, lon_lat1).maps(TO_DEGREE));
  }
}
