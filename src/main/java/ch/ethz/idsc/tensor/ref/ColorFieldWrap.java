// code by jph
package ch.ethz.idsc.tensor.ref;

import java.awt.Color;
import java.lang.reflect.Field;

import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.img.ColorFormat;
import ch.ethz.idsc.tensor.ref.gui.FieldPanel;

public class ColorFieldWrap extends BaseFieldWrap {
  public ColorFieldWrap(Field field) {
    super(field);
  }

  @Override // from FieldWrap
  public Object toValue(String string) {
    try {
      return ColorFormat.toColor(Tensors.fromString(string));
    } catch (Exception exception) {
      // ---
    }
    return null;
  }

  @Override // from FieldWrap
  public String toString(Object object) {
    return ColorFormat.toVector((Color) object).toString();
  }

  @Override
  public FieldPanel createFieldPanel(Object value) {
    return new ColorPanel(this, value);
  }
}
