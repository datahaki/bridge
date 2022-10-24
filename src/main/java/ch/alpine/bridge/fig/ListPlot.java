// code by gjoel, jph
package ch.alpine.bridge.fig;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.Optional;

import ch.alpine.bridge.awt.RenderQuality;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.alg.Transpose;
import ch.alpine.tensor.alg.VectorQ;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;

/** Hint:
 * to render list plot in a custom graphics use
 * jFreeChart.draw(graphics2d, rectangle2D)
 * 
 * to export to a graphics file use
 * ChartUtils.saveChartAsPNG(file, jFreeChart, width, height);
 * 
 * to embed figure as separate panel in gui use {@link ChartPanel}.
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/ListPlot.html">ListPlot</a> */
public class ListPlot implements Showable {
  private static final double RADIUS = 2.5;
  private final Tensor points;
  private final boolean joined;

  /** @param points of the form {{x1, y1}, {x2, y2}, ..., {xn, yn}}.
   * The special case when points == {} is also allowed.
   * @return instance of the visual row, that was added to this visual set
   * @throws Exception if not all entries in points are vectors of length 2 */
  public static Showable of(Tensor points) {
    return of(points, true);
  }

  /** @param points
   * @param joined
   * @return */
  public static Showable of(Tensor points, boolean joined) {
    return new ListPlot(points, joined);
  }

  /** @param domain {x1, x2, ..., xn}
   * @param values {y1, y2, ..., yn}
   * @return */
  public static Showable of(Tensor domain, Tensor tensor) {
    return of(Transpose.of(Tensors.of(domain, tensor)));
  }

  private ListPlot(Tensor points, boolean joined) {
    points.stream().forEach(row -> VectorQ.requireLength(row, 2));
    this.points = points;
    this.joined = joined;
  }

  @Override
  public void render(ShowableConfig showableConfig, Graphics _g) {
    if (0 < points.length()) {
      Graphics2D graphics = (Graphics2D) _g.create();
      RenderQuality.setQuality(graphics);
      graphics.setColor(color);
      if (joined) {
        Path2D.Double path = new Path2D.Double();
        {
          Point2D.Double point2d = showableConfig.toPoint2D(points.get(0));
          path.moveTo(point2d.x, point2d.y);
        }
        points.stream().skip(1).forEach(row -> {
          Point2D.Double point2d = showableConfig.toPoint2D(row);
          path.lineTo(point2d.x, point2d.y);
        });
        graphics.draw(path);
      } else {
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
      }
      graphics.dispose();
    }
  }

  @Override
  public Optional<CoordinateBoundingBox> fullPlotRange() {
    return Tensors.isEmpty(points) //
        ? Optional.empty()
        : Optional.of(CoordinateBoundingBox.of( //
            StaticHelper.minMax(points.get(Tensor.ALL, 0)), //
            StaticHelper.minMax(points.get(Tensor.ALL, 1))));
  }

  @Override
  public void setLabel(String string) {
    // TODO Auto-generated method stub
  }

  Color color;

  @Override
  public void setColor(Color color) {
    this.color = color;
  }
}
