// code by jph
package ch.alpine.bridge.ref;

import java.lang.reflect.Field;
import java.time.LocalTime;

/** for strings that can be parsed into {@link LocalTime}, for instance
 * 23:59
 * 23:59:45
 * 23:59:45.694872200 */
/* package */ class LocalTimeFieldWrap extends SelectableFieldWrap {
  public LocalTimeFieldWrap(Field field) {
    super(field);
  }

  @Override
  public LocalTime toValue(String string) {
    try {
      return LocalTime.parse(string);
    } catch (Exception exception) {
      // ---
    }
    return null;
  }
}
