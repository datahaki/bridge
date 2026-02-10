// code by jph
package ch.alpine.bridge.awt;

import java.awt.Image;
import java.awt.image.AffineTransformOp;
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
   * @param interpolationType
   * @return
   * @see AffineTransformOp#TYPE_NEAREST_NEIGHBOR
   * @see AffineTransformOp#TYPE_BILINEAR
   * @see AffineTransformOp#TYPE_BICUBIC */
  public Image getScaledInstance(ImageResize imageResize, int width, int height) {
    return cache.apply(Tensors.vector(imageResize.ordinal(), width, height));
  }

  private Image compute(Tensor wht) {
    int a = Round.intValueExact(wht.Get(0));
    int w = Round.intValueExact(wht.Get(1));
    int h = Round.intValueExact(wht.Get(2));
    return ImageResize.values()[a].of(bufferedImage, w, h);
  }
}
