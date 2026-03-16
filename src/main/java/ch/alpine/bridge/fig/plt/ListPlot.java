// code by gjoel, jph
package ch.alpine.bridge.fig.plt;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

import ch.alpine.bridge.fig.Showable;
import ch.alpine.bridge.fig.ShowableConfig;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.alg.Transpose;
import ch.alpine.tensor.api.ScalarUnaryOperator;

/** <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/ListPlot.html">ListPlot</a> */
public class ListPlot extends BasePointsPlot {
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

  public static Showable of(ScalarUnaryOperator suo, Tensor vector) {
    return of(Tensor.of(vector.stream() //
        .map(Scalar.class::cast) //
        .map(scalar -> Tensors.of(scalar, suo.apply(scalar)))));
  }

  // ---
  private double radius = RADIUS;

  private ListPlot(Tensor points) {
    super(points);
  }

  public void setPointsize(double radius) {
    this.radius = radius;
  }

  @Override // from Showable
  public void render(ShowableConfig showableConfig, Graphics2D graphics) {
    if (0 < points.length()) {
      graphics.setColor(getColor());
      for (Tensor row : points) {
        Point2D point2d = showableConfig.toPoint2D(row);
        graphics.setStroke(new BasicStroke());
        graphics.fill(new Ellipse2D.Double(point2d.getX() - radius, point2d.getY() - radius, 2 * radius, 2 * radius));
        // below: diamond <>
        // Path2D.Double path = new Path2D.Double();
        // path.moveTo(point2d.x + rad, point2d.y);
        // path.lineTo(point2d.x, point2d.y + rad);
        // path.lineTo(point2d.x - rad, point2d.y);
        // path.lineTo(point2d.x, point2d.y - rad);
        // graphics.fill(path);
      }
    }
  }
}
