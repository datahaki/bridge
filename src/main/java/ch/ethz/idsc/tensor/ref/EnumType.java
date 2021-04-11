// code by jph
package ch.ethz.idsc.tensor.ref;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.stream.Stream;

public class EnumType implements FieldIf {
  @Override
  public boolean isTracking(Class<?> cls) {
    return Enum.class.isAssignableFrom(cls);
  }

  @Override
  public Object toObject(Class<?> cls, String string) {
    return Stream.of(cls.getEnumConstants()) //
        .map(Enum.class::cast) //
        .filter(object -> object.name().equals(string)) //
        .findFirst() //
        .orElse(null);
  }

  @Override
  public String toString(Object object) {
    return ((Enum<?>) object).name();
  }

  public boolean isValidValue(Field field, Object object) {
    return Objects.nonNull(object) //
        && isTracking(object.getClass()); // default implementation
  }
}
