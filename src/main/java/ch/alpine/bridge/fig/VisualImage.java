// code by jph
package ch.alpine.bridge.fig;

import java.awt.image.BufferedImage;
import java.util.Objects;

import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.alg.Rescale;
import ch.alpine.tensor.img.ColorDataGradient;
import ch.alpine.tensor.io.ImageFormat;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;
import ch.alpine.tensor.sca.Clip;
import ch.alpine.tensor.sca.Clips;

public class VisualImage extends VisualBase {
  private final BufferedImage bufferedImage;

  /** @param clipX non-null
   * @param clipY non-null
   * @param bufferedImage non-null */
  public VisualImage(BufferedImage bufferedImage, Clip clipX, Clip clipY) {
    this.bufferedImage = Objects.requireNonNull(bufferedImage);
    getAxisX().setClip(clipX);
    getAxisY().setClip(clipY);
  }

  public VisualImage(BufferedImage bufferedImage, CoordinateBoundingBox coordinateBoundingBox) {
    this(bufferedImage, coordinateBoundingBox.getClip(0), coordinateBoundingBox.getClip(1));
  }

  /** @param bufferedImage */
  public VisualImage(BufferedImage bufferedImage) {
    this(bufferedImage, Clips.positive(bufferedImage.getWidth()), Clips.positive(bufferedImage.getHeight()));
  }

  /** @param matrix
   * @return black and white image */
  public static VisualImage of(Tensor matrix) {
    return new VisualImage(ImageFormat.of(Rescale.of(matrix).multiply(RealScalar.of(255))));
  }

  /** @param matrix
   * @param colorDataGradient
   * @return */
  public static VisualImage of(Tensor matrix, ColorDataGradient colorDataGradient) {
    return new VisualImage(ImageFormat.of(Rescale.of(matrix).map(colorDataGradient)));
  }

  /** @return */
  public BufferedImage getBufferedImage() {
    return bufferedImage;
  }
}
