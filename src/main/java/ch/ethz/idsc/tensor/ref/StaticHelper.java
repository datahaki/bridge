// code by jph
package ch.ethz.idsc.tensor.ref;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import ch.ethz.idsc.tensor.ext.Cache;

/* package */ enum StaticHelper {
  ;
  public static final Cache<Class<?>, Map<Field, FieldType>> CACHE = Cache.of(StaticHelper::build, 256);
  // ---
  private static final int MASK_FILTER = Modifier.PUBLIC;
  private static final int MASK_TESTED = MASK_FILTER //
      | Modifier.PRIVATE | Modifier.PROTECTED //
      | Modifier.FINAL | Modifier.STATIC | Modifier.TRANSIENT;

  /** @param field
   * @return whether field is public, non final, non static, non transient */
  private static final boolean isModified(Field field) {
    return (field.getModifiers() & MASK_TESTED) == MASK_FILTER;
  }

  private static Map<Field, FieldType> build(Class<?> init) {
    Deque<Class<?>> deque = new ArrayDeque<>();
    for (Class<?> cls = init; !cls.equals(Object.class); cls = cls.getSuperclass())
      deque.push(cls);
    Map<Field, FieldType> map = new LinkedHashMap<>();
    while (!deque.isEmpty())
      for (Field field : deque.pop().getDeclaredFields())
        if (isModified(field)) {
          Optional<FieldType> optional = FieldType.getTracking(field.getType());
          if (optional.isPresent())
            map.put(field, optional.get());
        }
    return Collections.unmodifiableMap(map);
  }
}
