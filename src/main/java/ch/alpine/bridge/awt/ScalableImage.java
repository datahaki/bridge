// code by jph
package ch.alpine.bridge.awt;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Objects;

import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.ext.Cache;

/** caches one scaled instance of a given BufferedImage so that repeated
 * computations for a specific width/height pair are skipped */
public class ScalableImage {
  private final Cache<Tensor, Image> cache = Cache.of(this::compute, 1);
  private final BufferedImage bufferedImage;
  private final int hints;

  /** @param bufferedImage
   * @param hints typically Image.SCALE_SMOOTH, or Image.SCALE_AREA_AVERAGING */
  public ScalableImage(BufferedImage bufferedImage, int hints) {
    this.bufferedImage = Objects.requireNonNull(bufferedImage);
    this.hints = hints;
  }

  /** @param width
   * @param height
   * @return */
  public Image getScaledInstance(int width, int height) {
    return cache.apply(Tensors.vector(width, height));
  }

  private Image compute(Tensor wh) {
    return bufferedImage.getScaledInstance( //
        wh.Get(0).number().intValue(), //
        wh.Get(1).number().intValue(), hints);
  }
}
