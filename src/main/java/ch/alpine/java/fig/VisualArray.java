// code by gjoel, jph
package ch.alpine.java.fig;

import java.util.Optional;

import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.img.ColorDataGradient;
import ch.alpine.tensor.opt.nd.Box;
import ch.alpine.tensor.qty.QuantityUnit;

public class VisualArray extends VisualBase {
  private ColorDataGradient colorDataGradient = null;
  private final Box box;
  @SuppressWarnings("unused")
  private final Tensor matrix;

  public VisualArray(Box box, Tensor matrix) {
    this.box = box;
    getAxisX().setUnit(QuantityUnit.of(box.getClip(0).min()));
    getAxisY().setUnit(QuantityUnit.of(box.getClip(1).min()));
    this.matrix = matrix;
  }

  /** @return value may be null */
  public ColorDataGradient getColorDataGradient() {
    return this.colorDataGradient;
  }

  /** @param colorDataGradient */
  public void setColorDataGradient(ColorDataGradient colorDataGradient) {
    this.colorDataGradient = colorDataGradient;
  }

  /** @param fallback
   * @return color data gradient set by user, otherwise given fallback */
  public ColorDataGradient getColorDataGradientOrElse(ColorDataGradient fallback) {
    return Optional.ofNullable(this.colorDataGradient).orElse(fallback);
  }

  public Box getBox() {
    return box;
  }
}
