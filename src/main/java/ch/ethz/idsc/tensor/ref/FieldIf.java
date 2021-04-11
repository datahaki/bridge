package ch.ethz.idsc.tensor.ref;

import java.lang.reflect.Field;

public interface FieldIf {
  boolean isTracking(Class<?> cls);

  Object toObject(Class<?> cls, String string);

  String toString(Object object);

  boolean isValidValue(Field field, Object object);
}
