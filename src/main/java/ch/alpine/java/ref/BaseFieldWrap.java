// code by jph
package ch.alpine.java.ref;

import java.lang.reflect.Field;
import java.util.Objects;

/* package */ abstract class BaseFieldWrap implements FieldWrap {
  private final Field field;

  public BaseFieldWrap(Field field) {
    this.field = Objects.requireNonNull(field);
  }

  @Override // from FieldWrap
  public final Field getField() {
    return field;
  }

  @Override // from FieldWrap
  public boolean isValidValue(Object value) {
    return true;
  }

  @Override
  public final void setIfValid(Object object, String string) {
    try {
      Object value = toValue(string);
      if (Objects.nonNull(value) && isValidValue(value)) // otherwise retain current assignment
        field.set(object, value);
    } catch (Exception exception) {
      exception.printStackTrace();
    }
  }
}