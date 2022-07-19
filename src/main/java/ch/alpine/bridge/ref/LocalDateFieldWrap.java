// code by jph
package ch.alpine.bridge.ref;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/** for strings that can be parsed into {@link LocalDate}, for instance
 * 2022-06-22 */
/* package */ class LocalDateFieldWrap extends BaseFieldWrap {
  public LocalDateFieldWrap(Field field) {
    super(field);
  }

  @Override
  public LocalDate toValue(String string) {
    Objects.requireNonNull(string);
    try {
      return LocalDate.parse(string);
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
    return new LocalDatePanel(this,  (LocalDate) value);
  }

}
