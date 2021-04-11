package ch.ethz.idsc.tensor.ref;

import java.lang.reflect.Field;
import java.util.Objects;

public class BooleanType implements FieldIf {
  @Override
  public boolean isTracking(Class<?> cls) {
    return Boolean.class.equals(cls);
  }

  @Override
  public Object toObject(Class<?> cls, String string) {
    return BooleanParser.orNull(string);
  }

  @Override
  public String toString(Object object) {
    return object.toString();
  }

  @Override
  public boolean isValidValue(Field field, Object object) {
    return Objects.nonNull(object) //
        && isTracking(object.getClass()); // default implementation
  }
}
