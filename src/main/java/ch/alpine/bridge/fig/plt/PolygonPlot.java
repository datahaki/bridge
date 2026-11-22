// code by jph
package ch.alpine.bridge.fig.plt;

import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;

import ch.alpine.bridge.fig.PlotOption;
import ch.alpine.bridge.fig.Showable;
import ch.alpine.bridge.fig.ShowableConfig;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.Unprotect;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;
import ch.alpine.tensor.sca.Clip;

/** <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Polygon.html">Polygon</a> */
public class PolygonPlot extends BasePointsPlot {
  /** @param points of the form {{x1, y1}, {x2, y2}, ..., {xn, yn}}
   * The special case when points == {} is also allowed.
   * @return instance of the visual row, that was added to this visual set
   * @throws Exception if not all entries in points are vectors of length 2 */
  public static Showable of(Tensor points, PlotOption... plotOptions) {
    Set<PlotOption> set = EnumSet.of(PlotOption.CLOSE);
    Arrays.stream(plotOptions).forEach(set::add);
    return new PolygonPlot(points, set);
  }

  public static Showable of(CoordinateBoundingBox cbb, PlotOption... plotOptions) {
    return of(polygon(cbb), plotOptions);
  }

  /** @param cbb
   * @return polygon defined by the corners of the first two dimensions of given
   * coordinateBoundingBox starting with the min,min corner and visiting ccw
   * so that PolygonArea gives a non-negative number */
  public static Tensor polygon(CoordinateBoundingBox cbb) {
    Clip clipX = cbb.clip(0);
    Clip clipY = cbb.clip(1);
    return Unprotect.byRef( //
        Tensors.of(clipX.min(), clipY.min()), //
        Tensors.of(clipX.max(), clipY.min()), //
        Tensors.of(clipX.max(), clipY.max()), //
        Tensors.of(clipX.min(), clipY.max()));
  }

  // ---
  private final Set<PlotOption> set;
  private final Path2D.Double path;

  PolygonPlot(Tensor points, Set<PlotOption> set) {
    super(points);
    path = new Path2D.Double(PathIterator.WIND_NON_ZERO, points.length());
    this.set = set;
  }

  @Override // from Showable
  public void render(ShowableConfig showableConfig, Graphics2D graphics) {
    if (0 < points.length()) {
      graphics.setColor(getColor());
      graphics.setStroke(getStroke());
      path.reset();
      {
        Point2D point2d = showableConfig.model2point(points.get(0));
        path.moveTo(point2d.getX(), point2d.getY());
      }
      points.stream().skip(1).forEach(row -> {
        Point2D point2d = showableConfig.model2point(row);
        path.lineTo(point2d.getX(), point2d.getY());
      });
      if (set.contains(PlotOption.CLOSE))
        path.closePath();
      if (set.contains(PlotOption.FILL))
        graphics.fill(path);
      else
        graphics.draw(path);
    }
  }
}
