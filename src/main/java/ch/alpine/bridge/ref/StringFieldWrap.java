// code by jph
package ch.alpine.bridge.ref;

import java.lang.reflect.Field;
import java.util.Objects;

/* package */ class StringFieldWrap extends SelectableFieldWrap {
  public StringFieldWrap(Field field) {
    super(field);
  }

  @Override // from FieldWrap
  public Object toValue(String string) {
    return Objects.requireNonNull(string);
  }

  @Override // from FieldWrap
  public String toString(Object value) {
    return value.toString();
  }
}
