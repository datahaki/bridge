// code by gjoel, jph
package ch.alpine.java.fig;

import java.awt.image.BufferedImage;
import java.util.Objects;

import ch.alpine.tensor.opt.nd.Box;
import ch.alpine.tensor.qty.QuantityUnit;

public class VisualArray extends VisualBase {
  private final Box box;
  private final BufferedImage bufferedImage;

  public VisualArray(Box box, BufferedImage bufferedImage) {
    this.box = box;
    getAxisX().setUnit(QuantityUnit.of(box.getClip(0).min()));
    getAxisY().setUnit(QuantityUnit.of(box.getClip(1).min()));
    this.bufferedImage = Objects.requireNonNull(bufferedImage);
  }

  /** @return */
  public Box getBox() {
    return box;
  }

  /** @return */
  public BufferedImage getBufferedImage() {
    return bufferedImage;
  }
}
