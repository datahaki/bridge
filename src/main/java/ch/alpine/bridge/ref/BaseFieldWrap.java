// code by jph
package ch.alpine.bridge.ref;

import java.lang.reflect.Field;
import java.util.Objects;

/** base class for all implementations of {@link FieldWrap} */
/* package */ abstract class BaseFieldWrap implements FieldWrap {
  private final Field field;

  /** @param field non-null */
  protected BaseFieldWrap(Field field) {
    this.field = Objects.requireNonNull(field);
  }

  @Override // from FieldWrap
  public final Field getField() {
    return field;
  }

  @Override // from FieldWrap
  public boolean isValidValue(Object value) {
    Objects.requireNonNull(value);
    return true;
  }

  @Override // from FieldWrap
  public final void setIfValid(Object object, String string) {
    Object value = toValue(string);
    if (Objects.nonNull(value) && isValidValue(value)) // else retain current assignment
      try {
        field.set(object, value);
      } catch (Exception exception) {
        throw new RuntimeException(exception);
      }
  }
}
