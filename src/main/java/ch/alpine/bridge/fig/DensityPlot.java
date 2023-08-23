// code by jph
package ch.alpine.bridge.fig;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.Objects;
import java.util.Optional;

import ch.alpine.bridge.awt.ScalableImage;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.alg.Rescale;
import ch.alpine.tensor.alg.Subdivide;
import ch.alpine.tensor.api.ScalarBinaryOperator;
import ch.alpine.tensor.api.ScalarTensorFunction;
import ch.alpine.tensor.img.ColorDataGradients;
import ch.alpine.tensor.io.ImageFormat;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;
import ch.alpine.tensor.sca.Clip;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/DensityPlot.html">DensityPlot</a> */
public class DensityPlot extends BarLegendPlot {
  public static DensityPlot of(ScalarBinaryOperator sbo, CoordinateBoundingBox cbb) {
    return of(sbo, cbb, ColorDataGradients.DENSITY);
  }

  public static DensityPlot of(ScalarBinaryOperator sbo, CoordinateBoundingBox cbb, ScalarTensorFunction colorDataGradient) {
    return new DensityPlot(sbo, cbb, colorDataGradient);
  }

  // ---
  private final ScalarBinaryOperator sbo;
  private final CoordinateBoundingBox cbb;
  private final ScalarTensorFunction colorDataGradient;

  private class Inner {
    private final ScalableImage scalableImage;
    private final Clip clip;

    public Inner() {
      Tensor dx = Subdivide.increasing(cbb.clip(0), resolution);
      Tensor dy = Subdivide.decreasing(cbb.clip(1), resolution);
      Tensor matrix = Tensor.of(dy.stream().parallel() //
          .map(Scalar.class::cast) //
          .map(y -> Tensor.of(dx.stream().map(Scalar.class::cast).map(x -> sbo.apply(x, y)))));
      Rescale rescale = new Rescale(matrix);
      BufferedImage bufferedImage = ImageFormat.of(rescale.result().map(colorDataGradient));
      scalableImage = new ScalableImage(bufferedImage, Image.SCALE_AREA_AVERAGING);
      clip = rescale.clip();
    }
  }

  private Inner inner = null;
  private int resolution = 25;

  private DensityPlot( //
      ScalarBinaryOperator sbo, //
      CoordinateBoundingBox cbb, //
      ScalarTensorFunction colorDataGradient) {
    super(colorDataGradient);
    this.sbo = sbo;
    this.cbb = cbb;
    this.colorDataGradient = colorDataGradient;
  }

  @Override // from Showable
  public void render(ShowableConfig showableConfig, Graphics2D graphics) {
    Point2D ul = showableConfig.toPoint2D(Tensors.of( //
        cbb.clip(0).min(), //
        cbb.clip(1).max()));
    Point2D dr = showableConfig.toPoint2D(Tensors.of( //
        cbb.clip(0).max(), //
        cbb.clip(1).min()));
    int width = (int) Math.floor(dr.getX() - ul.getX()) + 1;
    int height = (int) Math.floor(dr.getY() - ul.getY()) + 1;
    if (0 < width && 0 < height) {
      graphics.drawImage( //
          inner().scalableImage.getScaledInstance(width, height), //
          (int) ul.getX(), //
          (int) ul.getY(), //
          null);
    }
  }

  private Inner inner() {
    if (Objects.isNull(inner))
      inner = new Inner();
    return inner;
  }

  @Override // from Showable
  public Optional<CoordinateBoundingBox> fullPlotRange() {
    return Optional.of(cbb);
  }

  @Override
  protected Clip clip() {
    return inner().clip;
  }

  public void setPlotPoints(int resolution) {
    this.resolution = resolution;
    inner = null;
  }

  public int getPlotPoints() {
    return resolution;
  }
}
