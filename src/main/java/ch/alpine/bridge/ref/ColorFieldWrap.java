// code by jph
package ch.alpine.bridge.ref;

import java.awt.Color;
import java.lang.reflect.Field;
import java.util.Objects;

import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.img.ColorFormat;

/* package */ class ColorFieldWrap extends BaseFieldWrap {
  public ColorFieldWrap(Field field) {
    super(field);
  }

  @Override // from FieldWrap
  public Color toValue(String string) {
    Objects.requireNonNull(string);
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
  public FieldPanel createFieldPanel(Object object, Object value) {
    return new ColorPanel(this, value);
  }
}
