// code by jph
package ch.alpine.bridge.ref.util;

import java.lang.reflect.Field;

/* package */ enum StaticHelper {
  ;
  /** @param field
   * @param object
   * @return object.field_value */
  public static Object get(Field field, Object object) {
    try {
      return field.get(object);
    } catch (IllegalAccessException illegalAccessException) {
      throw new RuntimeException(illegalAccessException);
    }
  }
}
