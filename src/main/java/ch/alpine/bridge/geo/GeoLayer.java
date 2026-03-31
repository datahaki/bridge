// code by jph
package ch.alpine.bridge.geo;

import java.awt.Point;

import ch.alpine.tensor.Tensor;

public record GeoLayer(TilePixel origin) {
  public Point toPoint(Tensor lat_lon) {
    return toPoint(origin.from(lat_lon));
  }

  public Point toPoint(TilePixel tilePixel) {
    return new Point( //
        (int) (tilePixel.absX() - origin.absX()), //
        (int) (tilePixel.absY() - origin.absY()));
  }
}
