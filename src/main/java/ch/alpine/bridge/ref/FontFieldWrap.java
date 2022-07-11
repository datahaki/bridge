// code by jph
package ch.alpine.bridge.ref;

import java.awt.Font;
import java.lang.reflect.Field;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

/** for strings that can be parsed into {@link LocalTime}, for instance
 * 23:59
 * 23:59:45
 * 23:59:45.694872200 */
/* package */ class FontFieldWrap extends BaseFieldWrap {
  public FontFieldWrap(Field field) {
    super(field);
  }

  @Override
  public Font toValue(String string) {
    Objects.requireNonNull(string);
    return FontParser.toFont(string);
  }

  @Override
  public String toString(Object value) {
    Font font = (Font) value;
    return FontParser.toString(font);
  }

  @Override // from FieldWrap
  public List<Object> options(Object object) {
    return List.of();
  }

  @Override // from FieldWrap
  public FieldPanel createFieldPanel(Object object, Object value) {
    return new FontPanel(this, (Font) value);
  }
}
