// code by jph
package ch.alpine.bridge.ref;

import java.lang.reflect.Field;
import java.util.List;

public interface FieldWrap {
  /** @return field non-null wrapped by this instance */
  Field getField();

  /** @param string non-null
   * @return value parsed from string. note that the parsed value might
   * violate constraints defined by field annotations. If the given string
   * cannot be parsed, then null is returned.
   * @throws Exception if given string is null */
  Object toValue(String string);

  /** @param value non-null
   * @return string expression of value
   * @throws Exception if given value is null */
  default String toString(Object value) {
    return value.toString();
  }

  /** @param value non-null that may be cast to class associated to field
   * @return whether additional constraints defined by field annotations are satisfied
   * @throws Exception if given value is null, or given value cannot be cast to class
   * associated to field wrapped by this instance */
  boolean isValidValue(Object value);

  /** @param object
   * @param string non-null
   * @throws Exception if given string is null, or set operation failed */
  void setIfValid(Object object, String string);

  /** @param object
   * @return list of values that are available for this field of given object
   * for convenient selection by user */
  List<Object> options(Object object);

  /** @param object
   * @param value may be null
   * @return */
  FieldPanel createFieldPanel(Object object, Object value);
}
