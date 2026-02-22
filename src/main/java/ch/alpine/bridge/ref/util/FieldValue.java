// code by jph
package ch.alpine.bridge.ref.util;

import java.lang.reflect.Field;

/** @param field */
/* package */ record FieldValue(Field field) {
  /** @param object
   * @return object.field_value */
  public Object get(Object object) {
    try {
      field.trySetAccessible();
      return field.get(object);
    } catch (IllegalAccessException illegalAccessException) {
      throw new RuntimeException(illegalAccessException);
    }
  }
}
