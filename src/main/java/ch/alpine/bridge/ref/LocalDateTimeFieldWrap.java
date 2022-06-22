// code by jph
package ch.alpine.bridge.ref;

import java.lang.reflect.Field;
import java.time.LocalDateTime;

/** for strings that can be parsed into {@link LocalDateTime}, for instance
 * 2022-06-22T23:57
 * 2022-06-22T21:38:47
 * 2022-06-22T18:10:03.694030904 */
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
