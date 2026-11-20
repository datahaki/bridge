// code by jph
package ch.alpine.bridge.fig.plt;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import ch.alpine.bridge.col.Colors;
import ch.alpine.bridge.fig.Showable;
import ch.alpine.bridge.fig.ShowableConfig;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/DiscretePlot.html">DiscretePlot</a> */
public class ListDiscretePlot extends BasePointsPlot {
  /** @param points of the form {{x1, y1}, {x2, y2}, ..., {xn, yn}}
   * The special case when points == {} is also allowed.
   * @return instance of the visual row, that was added to this visual set
   * @throws Exception if not all entries in points are vectors of length 2 */
  public static Showable of(Tensor points) {
    return new ListDiscretePlot(points);
  }

  /** @param domain {x1, x2, ..., xn}
   * @param values {y1, y2, ..., yn}
   * @return */
  public static Showable of(Tensor domain, Tensor tensor) {
    return of(StaticHelper.pair(domain, tensor));
  }
  // ---

  private ListDiscretePlot(Tensor points) {
    super(points);
  }

  @Override
  public void render(ShowableConfig showableConfig, Graphics2D graphics) {
    Color color = Colors.withAlpha(getColor(), 64);
    double radius = 2.5;
    graphics.setStroke(getStroke());
    for (Tensor xy : points) {
      Scalar x = xy.Get(0);
      Scalar y = xy.Get(1);
      Point2D point2d = showableConfig.model2point(Tensors.of(x, y));
      double y0 = showableConfig.confY().model2pixel(y.zero());
      graphics.setColor(color);
      graphics.draw(new Line2D.Double(point2d.getX(), y0, point2d.getX(), point2d.getY()));
      graphics.setColor(getColor());
      graphics.fill(new Ellipse2D.Double(point2d.getX() - radius, point2d.getY() - radius, 2 * radius, 2 * radius));
    }
  }
}
