// code by jph
package ch.alpine.bridge.ref;

import java.lang.reflect.Field;
import java.time.LocalDate;

/** for strings that can be parsed into {@link LocalDate}, for instance
 * 2022-06-22 */
/* package */ class LocalDateFieldWrap extends SelectableFieldWrap {
  public LocalDateFieldWrap(Field field) {
    super(field);
  }

  @Override
  public LocalDate toValue(String string) {
    try {
      return LocalDate.parse(string);
    } catch (Exception exception) {
      // ---
    }
    return null;
  }
}
