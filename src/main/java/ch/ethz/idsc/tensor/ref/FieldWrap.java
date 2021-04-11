// code by jph
package ch.ethz.idsc.tensor.ref;

import java.lang.reflect.Field;

import ch.ethz.idsc.tensor.ref.gui.FieldPanel;

public interface FieldWrap {
  /** @return field */
  Field getField();

  /** @param string
   * @return value parsed from string. note that the parsed value might
   * violate constraints defined by field annotations. If the given string
   * cannot be parsed null is returned. */
  Object toValue(String string);

  /** @param value
   * @return string expression of value */
  String toString(Object value);

  /** @param value
   * @return whether additional constraints defined by field annotations are
   * satisfied */
  boolean isValidValue(Object value);

  /** @param value
   * @return */
  FieldPanel createFieldPanel(Object value);
}
