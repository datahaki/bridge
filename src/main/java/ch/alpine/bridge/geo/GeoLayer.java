// code by jph
package ch.alpine.bridge.geo;

import java.awt.Point;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;

import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;

public record GeoLayer(TilePixel origin) {
  public Point toPoint(Tensor lat_lon) {
    return toPoint(origin.from(lat_lon));
  }

  public Point toPoint(TilePixel tilePixel) {
    return new Point( //
        (int) (tilePixel.absX() - origin.absX()), //
        (int) (tilePixel.absY() - origin.absY()));
  }

  public Path2D toPath2D(Tensor polygon) {
    Path2D path2d = new Path2D.Double(PathIterator.WIND_NON_ZERO, polygon.length());
    toPath2D(path2d, polygon);
    return path2d;
  }

  /** @param path2d to which moveTo and lineTo directives are written
   * @param polygon */
  public void toPath2D(Path2D path2d, Tensor polygon) {
    if (Tensors.nonEmpty(polygon)) {
      Point2D point2d = toPoint(polygon.get(0));
      path2d.moveTo(point2d.getX(), point2d.getY());
    }
    polygon.stream() //
        .skip(1) // first coordinate already used in moveTo
        .map(this::toPoint) //
        .forEach(point2d -> path2d.lineTo(point2d.getX(), point2d.getY()));
  }

  /** @param polygon
   * @param close
   * @return path that is closed if given parameter close is true */
  public Path2D toPath2D(Tensor polygon, boolean close) {
    Path2D path2d = toPath2D(polygon);
    if (close)
      path2d.closePath();
    return path2d;
  }
}
