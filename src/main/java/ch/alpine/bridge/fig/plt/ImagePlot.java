// code by jph
package ch.alpine.bridge.fig.plt;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Optional;

import ch.alpine.bridge.awt.ScalableImage;
import ch.alpine.bridge.fig.BackgroundPlotMarker;
import ch.alpine.bridge.fig.BarLegend;
import ch.alpine.bridge.fig.BarLegendPlot;
import ch.alpine.bridge.fig.ShowableConfig;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
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
    Rectangle rectangle = showableConfig.rectangle();
    if (0 < rectangle.width && 0 < rectangle.height)
      graphics.drawImage(scalableImage.getScaledInstance(imageResize, rectangle.width, rectangle.height), 0, 0, null);
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
