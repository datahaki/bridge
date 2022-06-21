// code by jph
package ch.alpine.bridge.ref;

import java.lang.reflect.Field;
import java.time.LocalDateTime;

/* package */ class LocalDateTimeFieldWrap extends SelectableFieldWrap {
  public LocalDateTimeFieldWrap(Field field) {
    super(field);
  }

  @Override
  public LocalDateTime toValue(String string) {
    try {
      return LocalDateTime.parse(string);
    } catch (Exception exception) {
      // ---
    }
    return null;
  }
}
