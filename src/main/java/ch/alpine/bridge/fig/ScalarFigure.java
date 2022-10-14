// code by jph
package ch.alpine.bridge.fig;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.geom.Path2D;

import ch.alpine.tensor.RationalScalar;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.api.ScalarUnaryOperator;
import ch.alpine.tensor.itp.LinearInterpolation;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;
import ch.alpine.tensor.sca.Clip;

public class ScalarFigure {
  private final Rectangle rectangle;
  private final Clip xRange;
  private final Clip yRange;

  public ScalarFigure(Rectangle rectangle, CoordinateBoundingBox cbb) {
    this.rectangle = rectangle; //
    this.xRange = cbb.getClip(0);
    this.yRange = cbb.getClip(1);
  }

  public void render(Graphics2D _g, Color color, Stroke stroke, //
      ScalarUnaryOperator suo, int segmentsPerPixel) {
    // --
    Graphics2D graphics = (Graphics2D) _g.create();
    graphics.clipRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    graphics.setStroke(stroke);
    graphics.setColor(color);
    graphics.draw(path(suo, segmentsPerPixel));
    graphics.dispose();
  }

  public Path2D.Double path(ScalarUnaryOperator suo, int segmentsPerPixel) {
    double y_height = rectangle.y + rectangle.height;
    Scalar factor = RealScalar.of(rectangle.height).divide(yRange.width());
    double ofsx = rectangle.x;
    Path2D.Double path = new Path2D.Double();
    {
      Scalar eval = suo.apply(xRange.min());
      path.moveTo(ofsx, y_height - eval.subtract(yRange.min()).multiply(factor).number().doubleValue());
    }
    ScalarUnaryOperator interpX = LinearInterpolation.of(xRange);
    final int size = rectangle.width * segmentsPerPixel;
    final double dx = 1.0 / segmentsPerPixel;
    for (int i = 1; i <= size; ++i) {
      ofsx += dx;
      // compute the xValue and yValue of the function at xPix
      Scalar eval = suo.apply(interpX.apply(RationalScalar.of(i, size)));
      path.lineTo(ofsx, y_height - eval.subtract(yRange.min()).multiply(factor).number().doubleValue());
    }
    return path;
  }
  // public void render(Graphics2D _g, BufferedImage bufferedImage, CoordinateBoundingBox ibb) {
  // Graphics2D graphics = (Graphics2D) _g.create();
  // graphics.clipRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
  // Image image = bufferedImage.getScaledInstance(width, height, Image.SCALE_AREA_AVERAGING);
  // graphics.drawImage(image, 0, 0, null);
  // graphics.dispose();
  // }
}
