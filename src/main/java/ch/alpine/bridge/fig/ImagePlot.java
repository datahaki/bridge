// code by jph
package ch.alpine.bridge.fig;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.Optional;

import ch.alpine.bridge.awt.ScalableImage;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;
import ch.alpine.tensor.sca.Clips;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/ArrayPlot.html">ArrayPlot</a> */
// TODO BRIDGE mouse wheel scales image around location of mouse!
public class ImagePlot extends BaseShowable {
  public static Showable of(BufferedImage bufferedImage, CoordinateBoundingBox cbb, boolean flipY, Scalar aspectRatio) {
    return new ImagePlot(bufferedImage, cbb, flipY, aspectRatio);
  }

  public static Showable of(BufferedImage bufferedImage, CoordinateBoundingBox cbb) {
    return of(bufferedImage, cbb, false, null);
  }

  public static Showable of(BufferedImage bufferedImage) {
    return of(bufferedImage, CoordinateBoundingBox.of( //
        AbstractGridPlot.CENTER_INT.apply(Clips.positive(bufferedImage.getWidth())), //
        AbstractGridPlot.CENTER_INT.apply(Clips.positive(bufferedImage.getHeight()))), //
        true, RealScalar.ONE);
  }

  // ---
  private final ScalableImage scalableImage;
  private final CoordinateBoundingBox cbb;
  private final boolean flipY;
  private final Scalar aspectRatio;
  private int interpolationType = AffineTransformOp.TYPE_NEAREST_NEIGHBOR;

  private ImagePlot(BufferedImage bufferedImage, CoordinateBoundingBox cbb, boolean flipY, Scalar aspectRatio) {
    this.scalableImage = new ScalableImage(bufferedImage);
    this.cbb = cbb;
    this.flipY = flipY;
    this.aspectRatio = aspectRatio;
  }

  public int getInterpolationType() {
    return interpolationType;
  }

  public void setInterpolationType(int interpolationType) {
    this.interpolationType = interpolationType;
  }

  @Override // from Showable
  public void render(ShowableConfig showableConfig, Graphics2D graphics) {
    Point2D ul = showableConfig.toPoint2D(Tensors.of( //
        cbb.clip(0).min(), //
        flipY ? cbb.clip(1).min() : cbb.clip(1).max()));
    Point2D dr = showableConfig.toPoint2D(Tensors.of( //
        cbb.clip(0).max(), //
        flipY ? cbb.clip(1).max() : cbb.clip(1).min()));
    int width = (int) Math.floor(dr.getX() - ul.getX()) + 1;
    int height = (int) Math.floor(dr.getY() - ul.getY()) + 1;
    if (0 < width && 0 < height)
      graphics.drawImage(scalableImage.getScaledInstance(width, height, interpolationType), //
          (int) ul.getX(), //
          (int) ul.getY(), null);
  }

  @Override // from Showable
  public Optional<CoordinateBoundingBox> fullPlotRange() {
    return Optional.of(cbb);
  }

  @Override // from Showable
  public boolean flipYAxis() {
    return flipY;
  }

  @Override // from Showable
  public Optional<Scalar> aspectRatioHint() {
    return Optional.ofNullable(aspectRatio);
  }
}
