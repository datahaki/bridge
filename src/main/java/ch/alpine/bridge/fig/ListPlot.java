// code by gjoel, jph
package ch.alpine.bridge.fig;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.Optional;

import ch.alpine.bridge.awt.RenderQuality;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.alg.Transpose;
import ch.alpine.tensor.alg.VectorQ;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;

/** <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/ListPlot.html">ListPlot</a> */
public class ListPlot extends BaseShowable {
  private static final double RADIUS = 2.5;

  /** @param points of the form {{x1, y1}, {x2, y2}, ..., {xn, yn}}
   * @return */
  public static Showable of(Tensor points) {
    return new ListPlot(points);
  }

  /** @param domain {x1, x2, ..., xn}
   * @param values {y1, y2, ..., yn}
   * @return */
  public static Showable of(Tensor domain, Tensor tensor) {
    return of(Transpose.of(Tensors.of(domain, tensor)));
  }

  // ---
  private final Tensor points;

  private ListPlot(Tensor points) {
    points.stream().forEach(row -> VectorQ.requireLength(row, 2));
    this.points = points;
  }

  @Override // from Showable
  public void render(ShowableConfig showableConfig, Graphics _g) {
    if (0 < points.length()) {
      Graphics2D graphics = (Graphics2D) _g.create();
      RenderQuality.setQuality(graphics);
      graphics.setColor(getColor());
      for (Tensor row : points) {
        Point2D.Double point2d = showableConfig.toPoint2D(row);
        graphics.fill(new Ellipse2D.Double(point2d.x - RADIUS, point2d.y - RADIUS, 2 * RADIUS, 2 * RADIUS));
        // below: diamond <>
        // Path2D.Double path = new Path2D.Double();
        // path.moveTo(point2d.x + rad, point2d.y);
        // path.lineTo(point2d.x, point2d.y + rad);
        // path.lineTo(point2d.x - rad, point2d.y);
        // path.lineTo(point2d.x, point2d.y - rad);
        // graphics.fill(path);
      }
      graphics.dispose();
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
