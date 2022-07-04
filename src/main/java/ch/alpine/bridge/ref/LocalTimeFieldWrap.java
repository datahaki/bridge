// code by jph
package ch.alpine.bridge.ref;

import java.lang.reflect.Field;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

/** for strings that can be parsed into {@link LocalTime}, for instance
 * 23:59
 * 23:59:45
 * 23:59:45.694872200 */
/* package */ class LocalTimeFieldWrap extends BaseFieldWrap {
  public LocalTimeFieldWrap(Field field) {
    super(field);
  }

  @Override
  public LocalTime toValue(String string) {
    Objects.requireNonNull(string);
    try {
      return LocalTime.parse(string);
    } catch (Exception exception) {
      // ---
    }
    return null;
  }

  @Override // from FieldWrap
  public String toString(Object value) {
    return value.toString();
  }

  @Override // from FieldWrap
  public List<String> options(Object object) {
    return List.of();
  }

  @Override // from FieldWrap
  public FieldPanel createFieldPanel(Object object, Object value) {
    return new LocalTimePanel(this, (LocalTime) value);
  }
}
