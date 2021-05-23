// code by jph
package ch.alpine.java.ref;

import java.lang.reflect.Field;
import java.util.Objects;

public class StringFieldWrap extends SelectableFieldWrap {
  public StringFieldWrap(Field field) {
    super(field);
  }

  @Override // from FieldWrap
  public Object toValue(String string) {
    return Objects.requireNonNull(string);
  }
}
