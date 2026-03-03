// code by jph
package ch.alpine.bridge.fig;

import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Stream;

import ch.alpine.tensor.Tensor;

/** <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Polygon.html">Polygon</a> */
public class PolygonPlot extends BasePointsPlot {
  public enum Option {
    FILL,
    CLOSE
  }

  /** @param points of the form {{x1, y1}, {x2, y2}, ..., {xn, yn}}
   * The special case when points == {} is also allowed.
   * @return instance of the visual row, that was added to this visual set
   * @throws Exception if not all entries in points are vectors of length 2 */
  public static Showable of(Tensor points, Option... options) {
    Set<Option> set = EnumSet.of(Option.CLOSE);
    Stream.of(options).forEach(set::add);
    return new PolygonPlot(points, set);
  }

  // ---
  private final Set<Option> set;

  PolygonPlot(Tensor points, Set<Option> set) {
    super(points);
    this.set = set;
  }

  @Override // from Showable
  public void render(ShowableConfig showableConfig, Graphics2D graphics) {
    if (0 < points.length()) {
      graphics.setColor(getColor());
      graphics.setStroke(getStroke());
      Path2D.Double path = new Path2D.Double();
      {
        Point2D point2d = showableConfig.toPoint2D(points.get(0));
        path.moveTo(point2d.getX(), point2d.getY());
      }
      points.stream().skip(1).forEach(row -> {
        Point2D point2d = showableConfig.toPoint2D(row);
        path.lineTo(point2d.getX(), point2d.getY());
      });
      if (set.contains(Option.CLOSE))
        path.closePath();
      if (set.contains(Option.FILL))
        graphics.fill(path);
      else
        graphics.draw(path);
    }
  }
}
