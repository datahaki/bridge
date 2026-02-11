// code by jph
package ch.alpine.bridge.fig;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.Optional;

import ch.alpine.bridge.awt.ScalableImage;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Scalars;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.alg.Rescale;
import ch.alpine.tensor.alg.Subdivide;
import ch.alpine.tensor.api.ScalarBinaryOperator;
import ch.alpine.tensor.api.ScalarTensorFunction;
import ch.alpine.tensor.ext.Cache;
import ch.alpine.tensor.img.ColorDataGradients;
import ch.alpine.tensor.img.ImageResize;
import ch.alpine.tensor.io.ImageFormat;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;
import ch.alpine.tensor.qty.Quantity;
import ch.alpine.tensor.qty.Timing;
import ch.alpine.tensor.sca.Clip;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/DensityPlot.html">DensityPlot</a> */
// TODO BRIDGE constrain area by initial Clip
public class DensityPlot extends BarLegendPlot {
  private static final Scalar RENDER_TIME_TARGET = Quantity.of(0.1, "s");
  private static final int RESOLUTION_DEFAULT = 80;
  private static final int RESOLUTION_MIN = 10;

  public static DensityPlot of(ScalarBinaryOperator sbo, CoordinateBoundingBox cbb) {
    return of(sbo, cbb, ColorDataGradients.DENSITY);
  }

  public static DensityPlot of(ScalarBinaryOperator sbo, CoordinateBoundingBox cbb, ScalarTensorFunction colorDataGradient) {
    return new DensityPlot(sbo, cbb, colorDataGradient);
  }

  // ---
  private final Cache<CoordinateBoundingBox, Inner> cache = Cache.of(this::recompute, 1);
  private final ScalarBinaryOperator sbo;
  private final CoordinateBoundingBox cbb;
  private final ScalarTensorFunction colorDataGradient;
  // ---
  private Clip inner_clip = null;
  private int resolution = RESOLUTION_DEFAULT;

  private class Inner {
    private final ScalableImage scalableImage;
    private final Clip clip;

    public Inner(CoordinateBoundingBox cbb, int resolution) {
      // TODO BRIDGE resolution based on aspect ratio and cbb ?
      Tensor dx = Subdivide.intermediate_increasing(cbb.clip(0), resolution);
      Tensor dy = Subdivide.intermediate_decreasing(cbb.clip(1), resolution);
      Tensor matrix = Tensor.of(dy.stream().parallel() //
          .map(Scalar.class::cast) //
          .map(y -> Tensor.of(dx.stream().map(Scalar.class::cast).map(x -> sbo.apply(x, y)))));
      Rescale rescale = new Rescale(matrix);
      BufferedImage bufferedImage = ImageFormat.of(rescale.result().maps(colorDataGradient));
      scalableImage = new ScalableImage(bufferedImage);
      clip = rescale.clip();
    }
  }

  private DensityPlot( //
      ScalarBinaryOperator sbo, //
      CoordinateBoundingBox cbb, //
      ScalarTensorFunction colorDataGradient) {
    super(colorDataGradient);
    this.sbo = sbo;
    this.cbb = cbb;
    this.colorDataGradient = colorDataGradient;
    setImageResize(ImageResize.DEGREE_3);
  }

  @Override // from Showable
  public void render(ShowableConfig showableConfig, Graphics2D graphics) {
    CoordinateBoundingBox cbb = showableConfig.getCbb();
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
          cache.apply(cbb).scalableImage.getScaledInstance(getImageResize(), width, height), //
          (int) ul.getX(), //
          (int) ul.getY(), //
          null);
      //
      graphics.setColor(Color.DARK_GRAY);
      graphics.drawString("" + resolution, (int) ul.getX(), //
          (int) ul.getY() + 10);
    }
  }

  private Inner recompute(CoordinateBoundingBox cbb) {
    Timing timing = Timing.started();
    Inner inner = new Inner(cbb, resolution);
    resolution += Scalars.lessThan(timing.seconds(), RENDER_TIME_TARGET) //
        ? +7
        : -7;
    resolution = Math.max(RESOLUTION_MIN, resolution);
    inner_clip = inner.clip;
    return inner;
  }

  @Override // from Showable
  public Optional<CoordinateBoundingBox> fullPlotRange() {
    return Optional.of(cbb);
  }

  @Override
  protected Clip clip() {
    return inner_clip;
  }

  public void setPlotPoints(int resolution) {
    this.resolution = resolution;
    cache.clear();
  }
}
