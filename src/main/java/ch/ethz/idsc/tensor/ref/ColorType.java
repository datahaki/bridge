// code by jph
package ch.ethz.idsc.tensor.ref;

import java.awt.Color;
import java.lang.reflect.Field;
import java.util.Objects;

import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.img.ColorFormat;

public class ColorType implements FieldIf {
  @Override
  public boolean isTracking(Class<?> cls) {
    return Color.class.equals(cls);
  }

  @Override
  public Object toObject(Class<?> cls, String string) {
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
  public boolean isValidValue(Field field, Object object) {
    return Objects.nonNull(object) //
        && isTracking(object.getClass()); // default implementation
  }
}
