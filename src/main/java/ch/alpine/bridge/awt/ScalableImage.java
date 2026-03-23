// code by jph
package ch.alpine.bridge.awt;

import java.awt.image.BufferedImage;
import java.util.Objects;

import ch.alpine.tensor.Scalar;
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
  record Key(ImageResize imageResize, int width, int height) {
  }

  private final Cache<Key, BufferedImage> cache = Cache.of(this::compute, 1);
  private final BufferedImage bufferedImage;

  /** @param bufferedImage */
  public ScalableImage(BufferedImage bufferedImage) {
    this.bufferedImage = Objects.requireNonNull(bufferedImage);
  }

  /** @param imageResize
   * @param width
   * @param height
   * @return */
  public BufferedImage getScaledInstance(ImageResize imageResize, int width, int height) {
    return cache.apply(new Key(imageResize, width, height));
  }

  /** @param imageResize
   * @param factor
   * @return */
  public BufferedImage getScaledInstance(ImageResize imageResize, Scalar factor) {
    Tensor wh = Tensors.vector(bufferedImage.getWidth(), bufferedImage.getHeight()).multiply(factor);
    return getScaledInstance(imageResize, //
        Round.intValueExact(wh.Get(0)), //
        Round.intValueExact(wh.Get(1)));
  }

  private BufferedImage compute(Key key) {
    return key.imageResize.of(bufferedImage, key.width, key.height);
  }
}
