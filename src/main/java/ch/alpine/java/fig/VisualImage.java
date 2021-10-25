// code by jph
package ch.alpine.java.fig;

import java.awt.image.BufferedImage;
import java.util.Objects;

import ch.alpine.tensor.qty.QuantityUnit;
import ch.alpine.tensor.sca.Clip;
import ch.alpine.tensor.sca.Clips;

public class VisualImage extends VisualBase {
  public static VisualImage of(BufferedImage bufferedImage) {
    return new VisualImage(bufferedImage, Clips.positive(bufferedImage.getWidth()), Clips.positive(bufferedImage.getHeight()));
  }

  // ---
  private final Clip clipX;
  private final Clip clipY;
  private final BufferedImage bufferedImage;

  /** @param clipX non-null
   * @param clipY non-null
   * @param bufferedImage non-null */
  public VisualImage(BufferedImage bufferedImage, Clip clipX, Clip clipY) {
    this.bufferedImage = Objects.requireNonNull(bufferedImage);
    this.clipX = clipX;
    this.clipY = clipY;
    getAxisX().setUnit(QuantityUnit.of(clipX.min()));
    getAxisY().setUnit(QuantityUnit.of(clipY.min()));
  }

  /** @return */
  public Clip getClipX() {
    return clipX;
  }

  public Clip getClipY() {
    return clipY;
  }

  /** @return */
  public BufferedImage getBufferedImage() {
    return bufferedImage;
  }
}
