// code by jph
package ch.ethz.idsc.tensor.ref;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import ch.ethz.idsc.tensor.ext.Cache;

/* package */ enum StaticHelper {
  ;
  public static final Cache<Class<?>, List<FieldWrap>> CACHE = Cache.of(StaticHelper::build, 256);
  // ---
  private static final int MASK_FILTER = Modifier.PUBLIC;
  private static final int MASK_TESTED = MASK_FILTER //
      | Modifier.PRIVATE | Modifier.PROTECTED //
      | Modifier.FINAL | Modifier.STATIC | Modifier.TRANSIENT;

  /** @param field
   * @return whether field is public, non final, non static, non transient */
  private static final boolean isWrapped(Field field) {
    return (field.getModifiers() & MASK_TESTED) == MASK_FILTER;
  }

  private static List<FieldWrap> build(Class<?> init) {
    Deque<Class<?>> deque = new ArrayDeque<>();
    for (Class<?> cls = init; !cls.equals(Object.class); cls = cls.getSuperclass())
      deque.push(cls);
    List<FieldWrap> list = new LinkedList<>();
    while (!deque.isEmpty())
      for (Field field : deque.pop().getDeclaredFields())
        if (isWrapped(field))
          Optional.ofNullable(FieldWraps.INSTANCE.wrap(field)).ifPresent(list::add);
    return Collections.unmodifiableList(list);
  }
}
