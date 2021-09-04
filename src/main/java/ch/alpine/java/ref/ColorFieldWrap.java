// code by jph
package ch.alpine.java.ref;

import java.awt.Color;
import java.lang.reflect.Field;

import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.img.ColorFormat;

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

  @Override // from FieldWrap
  public FieldPanel createFieldPanel(Object value) {
    return new ColorPanel(this, value);
  }
}
