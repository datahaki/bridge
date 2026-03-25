// code by jph
package ch.alpine.bridge.ref;

import java.lang.reflect.Field;
import java.util.Objects;

class StringFieldWrap extends SelectableFieldWrap {
  public StringFieldWrap(Field field) {
    super(field);
  }

  @Override // from FieldWrap
  public Object toValue(String string) {
    return Objects.requireNonNull(string);
  }
}
