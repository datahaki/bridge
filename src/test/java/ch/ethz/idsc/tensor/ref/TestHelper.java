package ch.ethz.idsc.tensor.ref;

import java.lang.reflect.Field;
import java.util.stream.Stream;

enum TestHelper {
  ;
  /** @param field
   * @return if field is managed by {@link ObjectProperties} */
  /* package */ public static boolean isTracked(Field field) {
    if (ObjectProperties.isModified(field)) {
      Class<?> cls = field.getType();
      return Stream.of(FieldType.values()) //
          .anyMatch(type -> type.isTracking(cls));
    }
    return false;
  }
}
