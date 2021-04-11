// code by jph
package ch.ethz.idsc.tensor.ref;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import ch.ethz.idsc.tensor.ext.Cache;

/* package */ enum StaticHelper {
  ;
  public static final Cache<Class<?>, List<FieldType>> CACHE = Cache.of(StaticHelper::build, 256);
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

  private static List<FieldType> build(Class<?> init) {
    Deque<Class<?>> deque = new ArrayDeque<>();
    for (Class<?> cls = init; !cls.equals(Object.class); cls = cls.getSuperclass())
      deque.push(cls);
    List<FieldType> list = new LinkedList<>();
    while (!deque.isEmpty())
      for (Field field : deque.pop().getDeclaredFields())
        if (isModified(field)) {
          FieldType optional = FieldTypes.getFieldType(field);
          if (Objects.nonNull(optional))
            list.add(optional);
        }
    return Collections.unmodifiableList(list);
  }
}