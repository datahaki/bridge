// code by jph
package ch.alpine.bridge.fig;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.Optional;

import ch.alpine.bridge.cal.ISO8601DateTimeFocus;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.alg.Rescale;
import ch.alpine.tensor.alg.Subdivide;
import ch.alpine.tensor.api.ScalarBinaryOperator;
import ch.alpine.tensor.api.ScalarTensorFunction;
import ch.alpine.tensor.img.ColorDataGradients;
import ch.alpine.tensor.io.ImageFormat;
import ch.alpine.tensor.mat.MatrixQ;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;
import ch.alpine.tensor.sca.Clip;
import ch.alpine.tensor.sca.Clips;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/DensityPlot.html">DensityPlot</a> */
public class DensityPlot extends BaseShowable {
  private static final int RESOLUTION = 25;

  public static Showable of(ScalarBinaryOperator sbo, CoordinateBoundingBox cbb) {
    return of(sbo, cbb, ColorDataGradients.DENSITY);
  }

  public static Showable of(ScalarBinaryOperator sbo, CoordinateBoundingBox cbb, ScalarTensorFunction colorDataGradient) {
    Tensor dx = Subdivide.increasing(cbb.getClip(0), RESOLUTION);
    Tensor dy = Subdivide.decreasing(cbb.getClip(1), RESOLUTION);
    Tensor matrix = Tensor.of(dy.stream().parallel() //
        .map(Scalar.class::cast) //
        .map(y -> Tensor.of(dx.stream().map(Scalar.class::cast).map(x -> sbo.apply(x, y)))));
    return new DensityPlot(matrix, cbb, colorDataGradient);
  }

  // ---
  private final BufferedImage bufferedImage;
  private final ScalarTensorFunction colorDataGradient;
  private final CoordinateBoundingBox cbb;
  private final Clip clip;

  private DensityPlot( //
      Tensor matrix, //
      CoordinateBoundingBox cbb, //
      ScalarTensorFunction colorDataGradient) {
    MatrixQ.require(matrix);
    Rescale rescale = new Rescale(matrix);
    this.bufferedImage = ImageFormat.of(rescale.result().map(colorDataGradient));
    this.colorDataGradient = colorDataGradient;
    this.cbb = cbb;
    this.clip = rescale.clip();
  }

  @Override // from Showable
  public void render(ShowableConfig showableConfig, Graphics2D graphics) {
    Point2D.Double ul = showableConfig.toPoint2D(Tensors.of( //
        cbb.getClip(0).min(), //
        cbb.getClip(1).max()));
    Point2D.Double dr = showableConfig.toPoint2D(Tensors.of( //
        cbb.getClip(0).max(), //
        cbb.getClip(1).min()));
    int width = (int) Math.floor(dr.getX() - ul.getX()) + 1;
    int height = (int) Math.floor(dr.getY() - ul.getY()) + 1;
    if (0 < width && 0 < height)
      graphics.drawImage(bufferedImage, //
          (int) ul.getX(), //
          (int) ul.getY(), //
          width, height, null);
  }

  @Override
  public void tender(ShowableConfig showableConfig, Graphics graphics) {
    Rectangle rectangle = showableConfig.rectangle;
    int width = StaticHelper.GAP * 2;
    int pix = rectangle.x + rectangle.width + 1 + StaticHelper.GAP * 2;
    graphics.drawImage(ImageFormat.of(Subdivide.decreasing(Clips.unit(), rectangle.height - 1).map(Tensors::of).map(colorDataGradient)), //
        pix, rectangle.y, width, rectangle.height, null);
    new AxisYR(ISO8601DateTimeFocus.INSTANCE).render( //
        showableConfig, //
        new Point(pix + width + StaticHelper.GAP - 2, rectangle.y), //
        rectangle.height, graphics, clip);
  }

  @Override // from Showable
  public Optional<CoordinateBoundingBox> fullPlotRange() {
    return Optional.of(cbb);
  }
}
