// code by legion
package ch.alpine.bridge.fig;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

import ch.alpine.bridge.awt.RenderQuality;
import ch.alpine.tensor.RationalScalar;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.api.ScalarUnaryOperator;
import ch.alpine.tensor.itp.LinearInterpolation;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;
import ch.alpine.tensor.sca.Clip;

public class ScalarFigure {
  private final Rectangle rectangle;
  private final Clip xRange;
  private final Clip yRange;
  private final double y_height; // TODO misnomer
  private final Scalar x_factor;
  private final Scalar y_factor;

  /** @param rectangle
   * @param cbb with positive area */
  public ScalarFigure(Rectangle rectangle, CoordinateBoundingBox cbb) {
    this.rectangle = new Rectangle(rectangle.x, rectangle.y, rectangle.width - 1, rectangle.height - 1);
    this.xRange = cbb.getClip(0);
    this.yRange = cbb.getClip(1);
    y_height = rectangle.y + rectangle.height;
    x_factor = RealScalar.of(rectangle.width).divide(xRange.width());
    y_factor = RealScalar.of(rectangle.height).divide(yRange.width());
  }

  public void render(Graphics2D _g, Color color, Stroke stroke, Tensor points) {
    if (0 < points.length()) {
      Graphics2D graphics = (Graphics2D) _g.create();
      graphics.setClip(rectangle.x, rectangle.y, rectangle.width + 1, rectangle.height + 1);
      graphics.setStroke(stroke);
      graphics.setColor(color);
      RenderQuality.setQuality(graphics);
      Path2D.Double path = new Path2D.Double();
      {
        Point2D.Double point2d = toPoint2D(points.get(0));
        path.moveTo(point2d.x, point2d.y);
      }
      points.stream().skip(1).forEach(row -> {
        Point2D.Double point2d = toPoint2D(row);
        path.lineTo(point2d.x, point2d.y);
      });
      graphics.draw(path);
      graphics.dispose();
    }
  }

  public void render(Graphics2D _g, Color color, Stroke stroke, //
      ScalarUnaryOperator suo, int segmentsPerPixel) {
    // --
    Graphics2D graphics = (Graphics2D) _g.create();
    graphics.setClip(rectangle.x, rectangle.y, rectangle.width + 1, rectangle.height + 1);
    graphics.setStroke(stroke);
    graphics.setColor(color);
    {
      double ofsx = rectangle.x;
      Path2D.Double path = new Path2D.Double();
      {
        Scalar eval = suo.apply(xRange.min());
        path.moveTo(ofsx, y_height - eval.subtract(yRange.min()).multiply(y_factor).number().doubleValue());
      }
      ScalarUnaryOperator interpX = LinearInterpolation.of(xRange);
      final int size = rectangle.width * segmentsPerPixel;
      final double dx = 1.0 / segmentsPerPixel;
      for (int i = 1; i <= size; ++i) {
        ofsx += dx;
        // compute the xValue and yValue of the function at xPix
        Scalar y_eval = suo.apply(interpX.apply(RationalScalar.of(i, size)));
        path.lineTo(ofsx, y_height - y_eval.subtract(yRange.min()).multiply(y_factor).number().doubleValue());
      }
      graphics.draw(path);
    }
    graphics.dispose();
  }

  private Point2D.Double toPoint2D(Tensor vector) {
    return new Point2D.Double( //
        rectangle.x + vector.Get(0).subtract(xRange.min()).multiply(x_factor).number().doubleValue(), //
        y_height - vector.Get(1).subtract(yRange.min()).multiply(y_factor).number().doubleValue());
  }
}
