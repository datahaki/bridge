// code by jph
package ch.ethz.idsc.tensor.ref;

import java.awt.Color;
import java.lang.reflect.Field;

import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.img.ColorFormat;
import ch.ethz.idsc.tensor.ref.gui.ColorPanel;
import ch.ethz.idsc.tensor.ref.gui.FieldPanel;

public class ColorType extends FieldBase {
  public ColorType(Field field) {
    super(field);
  }

  @Override
  public boolean isTracking(Class<?> cls) {
    return Color.class.equals(cls);
  }

  @Override
  public Object toObject(String string) {
    try {
      return ColorFormat.toColor(Tensors.fromString(string));
    } catch (Exception exception) {
      // ---
    }
    return null;
  }

  @Override
  public String toString(Object object) {
    return ColorFormat.toVector((Color) object).toString();
  }

  @Override
  public FieldPanel createFieldPanel(Object value) {
    return new ColorPanel(this, value);
  }
}
