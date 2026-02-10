// code by jph
package ch.alpine.bridge.awt;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Objects;

import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.ext.Cache;
import ch.alpine.tensor.img.ImageResize;
import ch.alpine.tensor.sca.Round;

/** caches one scaled instance of a given BufferedImage so that repeated
 * computations for a specific width/height pair are skipped
 * 
 * Careful: functionality is suitable for on-screen display.
 * When printing graphics always use the full resolution image for
 * maximum quality. */
public class ScalableImage {
  private final Cache<Tensor, Image> cache = Cache.of(this::compute, 1);
  private final BufferedImage bufferedImage;

  /** @param bufferedImage
   * @param hints typically Image.SCALE_SMOOTH, or Image.SCALE_AREA_AVERAGING */
  public ScalableImage(BufferedImage bufferedImage) {
    this.bufferedImage = Objects.requireNonNull(bufferedImage);
  }

  /** @param width
   * @param height
   * @return */
  // public Image getScaledInstance(int width, int height) {
  // return bufferedImage.getWidth() == width && bufferedImage.getHeight() == height //
  // ? bufferedImage
  // : cache.apply(Tensors.vector(width, height, interpolationType));
  // }
  public Image getScaledInstance(int width, int height, int interpolationType) {
    return cache.apply(Tensors.vector(width, height, interpolationType));
  }

  private Image compute(Tensor wh) {
    int w = Round.intValueExact(wh.Get(0));
    int h = Round.intValueExact(wh.Get(1));
    int interpolationType = Round.intValueExact(wh.Get(2));
    return ImageResize.of(bufferedImage, w, h, interpolationType);
  }
}
