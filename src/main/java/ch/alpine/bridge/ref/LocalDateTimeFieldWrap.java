// code by jph
package ch.alpine.bridge.ref;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/** for strings that can be parsed into {@link LocalDateTime}, for instance
 * 2022-06-22T23:57
 * 2022-06-22T21:38:47
 * 2022-06-22T18:10:03.694030904 */
/* package */ class LocalDateTimeFieldWrap extends BaseFieldWrap {
  public LocalDateTimeFieldWrap(Field field) {
    super(field);
  }

  @Override
  public LocalDateTime toValue(String string) {
    Objects.requireNonNull(string);
    try {
      return LocalDateTime.parse(string);
    } catch (Exception exception) {
      // ---
    }
    return null;
  }

  @Override
  public List<Object> options(Object object) {
    return List.of();
  }

  @Override
  public FieldPanel createFieldPanel(Object object, Object value) {
    // TODO Auto-generated method stub
    return new LocalDateTimePanel(this, (LocalDateTime) value);
  }
}
