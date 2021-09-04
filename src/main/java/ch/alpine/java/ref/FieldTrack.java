// code by jph
package ch.alpine.java.ref;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public enum FieldTrack {
  ;
  // public static final Cache<Class<?>, List<FieldWrap>> CACHE = Cache.of(FieldTrack::build, 256);
  // ---
  private static final int MASK_FILTER = Modifier.PUBLIC;
  private static final int MASK_TESTED = MASK_FILTER //
      | Modifier.PRIVATE | Modifier.PROTECTED //
      | Modifier.FINAL | Modifier.STATIC | Modifier.TRANSIENT;

  /** @param field
   * @return whether field is public, non final, non static, non transient */
  public static final boolean isWrapped(Field field) {
    return (field.getModifiers() & MASK_TESTED) == MASK_FILTER;
  }

  private static final int MASK1_FILTER = Modifier.PUBLIC | Modifier.FINAL;
  private static final int MASK1_TESTED = MASK1_FILTER //
      | Modifier.PRIVATE | Modifier.PROTECTED //
      | Modifier.FINAL | Modifier.STATIC | Modifier.TRANSIENT;
  
  public static final boolean isArray(Field field) {
    return (field.getModifiers() & MASK1_TESTED) == MASK1_FILTER;
  }
  //
  // private static List<FieldWrap> build(Class<?> init) {
  // Deque<Class<?>> deque = new ArrayDeque<>();
  // for (Class<?> cls = init; !cls.equals(Object.class); cls = cls.getSuperclass())
  // deque.push(cls);
  // List<FieldWrap> list = new LinkedList<>();
  // while (!deque.isEmpty())
  // for (Field field : deque.pop().getDeclaredFields())
  // if (isWrapped(field))
  // Optional.ofNullable(FieldWraps.INSTANCE.wrap(field)).ifPresent(list::add);
  // return Collections.unmodifiableList(list);
  // }
}
