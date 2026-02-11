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
// TODO implements Serializable
public class ScalableImage {
  private final BufferedImage bufferedImage;
  private final Cache<Tensor, BufferedImage> cache = Cache.of(this::compute, 1);

  /** @param bufferedImage */
  public ScalableImage(BufferedImage bufferedImage) {
    this.bufferedImage = Objects.requireNonNull(bufferedImage);
  }

  /** @param imageResize
   * @param width
   * @param height
   * @return */
  public BufferedImage getScaledInstance(ImageResize imageResize, int width, int height) {
    return cache.apply(Tensors.vector(imageResize.ordinal(), width, height));
  }

  public BufferedImage getScaledInstance(ImageResize imageResize, Scalar factor) {
    Tensor wh = Tensors.vector(bufferedImage.getWidth(), bufferedImage.getHeight()).multiply(factor);
    return getScaledInstance(imageResize, //
        Round.intValueExact(wh.Get(0)), //
        Round.intValueExact(wh.Get(1)));
  }

  /** @param owh {ordinal, width, height}
   * @return */
  private BufferedImage compute(Tensor owh) {
    int ordinal = Round.intValueExact(owh.Get(0));
    int width = Round.intValueExact(owh.Get(1));
    int height = Round.intValueExact(owh.Get(2));
    return ImageResize.values()[ordinal].of(bufferedImage, width, height);
  }
}
