// code by jph
package ch.alpine.bridge.fig;

import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.Optional;

import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.alg.Transpose;
import ch.alpine.tensor.alg.VectorQ;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;

/** <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/ListLinePlot.html">ListLinePlot</a> */
public class ListLinePlot extends BaseShowable {
  /** @param points of the form {{x1, y1}, {x2, y2}, ..., {xn, yn}}
   * The special case when points == {} is also allowed.
   * @return instance of the visual row, that was added to this visual set
   * @throws Exception if not all entries in points are vectors of length 2 */
  public static Showable of(Tensor points) {
    return new ListLinePlot(points);
  }

  /** @param domain {x1, x2, ..., xn}
   * @param values {y1, y2, ..., yn}
   * @return */
  public static Showable of(Tensor domain, Tensor tensor) {
    return of(Transpose.of(Tensors.of(domain, tensor)));
  }

  // ---
  private final Tensor points;

  private ListLinePlot(Tensor points) {
    points.forEach(row -> VectorQ.requireLength(row, 2));
    this.points = points;
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
      graphics.draw(path);
    }
  }

  @Override // from Showable
  public Optional<CoordinateBoundingBox> fullPlotRange() {
    return Tensors.isEmpty(points) //
        ? Optional.empty()
        : Optional.of(CoordinateBoundingBox.of( //
            StaticHelper.minMax(points.get(Tensor.ALL, 0)), //
            StaticHelper.minMax(points.get(Tensor.ALL, 1))));
  }
}
