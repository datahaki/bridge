// code by jph
package ch.alpine.bridge.fig;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.Optional;

import ch.alpine.bridge.awt.ScalableImage;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.ext.Integers;
import ch.alpine.tensor.img.ImageResize;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/ArrayPlot.html">ArrayPlot</a> */
// TODO BRIDGE the aspect ratio pipeline is NO GOOD !
// why isnt this instance of BaseArrayPlot, because BarLegendPlot (design not ideal)
public class ImagePlot extends BarLegendPlot implements BackgroundPlotMarker {
  /** @param bufferedImage
   * @param imageResize
   * @param cbb
   * @param barLegend
   * @param flipY
   * @param aspectRatio
   * @return */
  public static ImagePlot of( //
      BufferedImage bufferedImage, ImageResize imageResize, CoordinateBoundingBox cbb, BarLegend barLegend, boolean flipY, Scalar aspectRatio) {
    return new ImagePlot(new ScalableImage(bufferedImage), imageResize, cbb, barLegend, flipY, aspectRatio);
  }

  public static ImagePlot of( //
      BufferedImage bufferedImage, ImageResize imageResize, CoordinateBoundingBox cbb, boolean flipY, Scalar aspectRatio) {
    return of(bufferedImage, imageResize, cbb, null, flipY, aspectRatio);
  }

  public static ImagePlot of(BufferedImage bufferedImage, CoordinateBoundingBox cbb, boolean flipY, Scalar aspectRatio) {
    return of(bufferedImage, ImageResize.DEGREE_1, cbb, flipY, aspectRatio);
  }

  public static ImagePlot of(BufferedImage bufferedImage, CoordinateBoundingBox cbb) {
    return of(bufferedImage, cbb, false, null);
  }

  public static ImagePlot of(BufferedImage bufferedImage, ImageResize imageResize) {
    return of(bufferedImage, imageResize, StaticHelper.shift(bufferedImage), true, RealScalar.ONE);
  }

  public static ImagePlot of(BufferedImage bufferedImage) {
    return of(bufferedImage, ImageResize.DEGREE_1);
  }

  // ---
  private final ScalableImage scalableImage;
  private final ImageResize imageResize;
  private final BarLegend barLegend;
  private final boolean flipY;
  private final Scalar aspectRatio;

  protected ImagePlot( //
      ScalableImage scalableImage, //
      ImageResize imageResize, //
      CoordinateBoundingBox cbb, //
      BarLegend barLegend, boolean flipY, Scalar aspectRatio) {
    super(cbb);
    Integers.requireEquals(cbb.dimensions(), 2);
    this.scalableImage = scalableImage;
    this.imageResize = imageResize;
    this.barLegend = barLegend;
    this.flipY = flipY;
    this.aspectRatio = aspectRatio;
  }

  @Override // from Showable
  public final void render(ShowableConfig showableConfig, Graphics2D graphics) {
    Point2D ul = showableConfig.toPoint2D(Tensors.of( //
        cbb.clip(0).min(), //
        flipY ? cbb.clip(1).min() : cbb.clip(1).max()));
    Point2D dr = showableConfig.toPoint2D(Tensors.of( //
        cbb.clip(0).max(), //
        flipY ? cbb.clip(1).max() : cbb.clip(1).min()));
    // TODO for ArrayShowable the zoom should be limited
    int width = (int) Math.floor(dr.getX() - ul.getX()) + 1;
    int height = (int) Math.floor(dr.getY() - ul.getY()) + 1;
    if (0 < width && 0 < height)
      graphics.drawImage(scalableImage.getScaledInstance(imageResize, width, height), //
          (int) ul.getX(), //
          (int) ul.getY(), null);
  }

  @Override
  protected final BarLegend barLegend() {
    return barLegend;
  }

  @Override // from Showable
  public final boolean flipYAxis() {
    return flipY;
  }

  @Override // from Showable
  public final Optional<Scalar> aspectRatioHint() {
    return Optional.ofNullable(aspectRatio);
  }
}
