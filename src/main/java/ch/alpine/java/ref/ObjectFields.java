// code by jph
package ch.alpine.java.ref;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class ObjectFields {
  private static final int LEAF_FILTER = Modifier.PUBLIC;
  private static final int LEAF_TESTED = LEAF_FILTER //
      | Modifier.PRIVATE | Modifier.PROTECTED //
      | Modifier.FINAL | Modifier.STATIC | Modifier.TRANSIENT;
  private static final Predicate<Field> IS_LEAF = VisibilityPredicate.field( //
      LEAF_FILTER, //
      LEAF_TESTED);
  // ---
  private static final int NODE_FILTER = Modifier.PUBLIC | Modifier.FINAL;
  private static final int NODE_TESTED = NODE_FILTER //
      | Modifier.PRIVATE | Modifier.PROTECTED //
      | Modifier.FINAL | Modifier.STATIC | Modifier.TRANSIENT;
  private static final Predicate<Field> IS_NODE = VisibilityPredicate.field( //
      NODE_FILTER, //
      NODE_TESTED);

  /** @param object
   * @param objectFieldVisitor
   * @return
   * @throws Exception if any input parameter is null */
  public static void of(Object object, ObjectFieldVisitor objectFieldVisitor) {
    new ObjectFields(Objects.requireNonNull(objectFieldVisitor)).visit("", object);
  }

  // ---
  private final ObjectFieldVisitor objectFieldVisitor;

  private ObjectFields(ObjectFieldVisitor objectFieldVisitor) {
    this.objectFieldVisitor = objectFieldVisitor;
  }

  private void visit(String _prefix, Object object) {
    if (Objects.nonNull(object)) {
      Deque<Class<?>> deque = new ArrayDeque<>();
      for (Class<?> cls = object.getClass(); !cls.equals(Object.class); cls = cls.getSuperclass())
        deque.push(cls);
      while (!deque.isEmpty())
        for (Field field : deque.pop().getDeclaredFields()) {
          Class<?> class_field = field.getType();
          String prefix = _prefix + field.getName();
          if (FieldWraps.INSTANCE.elemental(class_field)) {
            if (IS_LEAF.test(field)) {
              FieldWrap fieldWrap = FieldWraps.INSTANCE.wrap(field);
              if (Objects.nonNull(fieldWrap))
                objectFieldVisitor.accept(prefix, fieldWrap, object, get(field, object));
            }
          } else {
            if (IS_NODE.test(field))
              if (class_field.isArray())
                iterate(prefix, field, Arrays.asList((Object[]) get(field, object)));
              else {
                if (field.getType().equals(List.class))
                  iterate(prefix, field, (List<?>) get(field, object));
                else {
                  objectFieldVisitor.push(prefix, field, null);
                  visit(prefix + ".", get(field, object));
                  objectFieldVisitor.pop();
                }
              }
          }
        }
    }
  }

  private void iterate(String prefix, Field field, List<?> list) throws IllegalArgumentException {
    for (int index = 0; index < list.size(); ++index) {
      String string = String.format("%s[%d]", prefix, index);
      objectFieldVisitor.push(string, field, index);
      visit(string + ".", list.get(index));
      objectFieldVisitor.pop();
    }
  }

  /** @param field
   * @param object
   * @return {@link Field#get(Object)} */
  private static Object get(Field field, Object object) {
    try {
      return field.get(object);
    } catch (Exception exception) {
      exception.printStackTrace();
    }
    throw new IllegalArgumentException();
  }
}
