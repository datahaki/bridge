package ch.ethz.idsc.tensor.ref;

import java.lang.reflect.Field;

import ch.ethz.idsc.tensor.ref.gui.FieldPanel;

public interface FieldType {
  Field getField();

  boolean isTracking(Class<?> cls);

  Object toObject(String string);

  String toString(Object object);

  boolean isValidValue(Object object);

  FieldPanel createFieldPanel(Object value);

  default boolean isValidString(String string) {
    return isValidValue(toObject(string));
  }
}
