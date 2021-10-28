// code by jph
package ch.alpine.java.fig;

import java.awt.image.BufferedImage;
import java.util.Objects;

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

  /** @param bufferedImage */
  public VisualImage(BufferedImage bufferedImage) {
    this(bufferedImage, Clips.positive(bufferedImage.getWidth()), Clips.positive(bufferedImage.getHeight()));
  }

  /** @return */
  public BufferedImage getBufferedImage() {
    return bufferedImage;
  }
}
