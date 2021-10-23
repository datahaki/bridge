// code by jph
package ch.alpine.java.fig;

import java.awt.image.BufferedImage;
import java.util.Objects;

import ch.alpine.tensor.qty.QuantityUnit;
import ch.alpine.tensor.sca.Clip;

public class VisualArray extends VisualBase {
  private final Clip clipX;
  private final Clip clipY;
  private final BufferedImage bufferedImage;

  /** @param clipX non-null
   * @param clipY non-null
   * @param bufferedImage non-null */
  public VisualArray(Clip clipX, Clip clipY, BufferedImage bufferedImage) {
    this.clipX = clipX;
    this.clipY = clipY;
    getAxisX().setUnit(QuantityUnit.of(clipX.min()));
    getAxisY().setUnit(QuantityUnit.of(clipY.min()));
    this.bufferedImage = Objects.requireNonNull(bufferedImage);
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
