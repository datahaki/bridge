// code by jph
package ch.alpine.java.ref;

import java.lang.reflect.Field;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;

public class ObjectFields {
  /** @param object
   * @param objectFieldVisitor
   * @return */
  public static void of(Object object, ObjectFieldVisitor objectFieldVisitor) {
    new ObjectFields(objectFieldVisitor).visit("", object);
  }

  // ==================================================
  private final ObjectFieldVisitor objectFieldVisitor;

  private ObjectFields(ObjectFieldVisitor objectFieldVisitor) {
    this.objectFieldVisitor = objectFieldVisitor;
  }

  private void visit(String _prefix, Object object) {
    Deque<Class<?>> deque = new ArrayDeque<>();
    for (Class<?> cls = object.getClass(); !cls.equals(Object.class); cls = cls.getSuperclass())
      deque.push(cls);
    while (!deque.isEmpty())
      for (Field field : deque.pop().getDeclaredFields()) {
        Class<?> class_field = field.getType();
        String prefix = _prefix + field.getName();
        try {
          if (FieldWraps.INSTANCE.elemental(class_field)) {
            if (FieldTrack.isWrapped(field)) {
              FieldWrap fieldWrap = FieldWraps.INSTANCE.wrap(field);
              if (Objects.nonNull(fieldWrap))
                objectFieldVisitor.accept(prefix, fieldWrap, object, field.get(object));
            }
          } else {
            if (FieldTrack.isNested(field))
              if (!class_field.isArray()) {
                objectFieldVisitor.push(prefix);
                visit(prefix + ".", field.get(object));
                objectFieldVisitor.pop();
              } else {
                Object[] array = (Object[]) field.get(object);
                final int length = array.length;
                Class<?> class_element = class_field.getComponentType();
                if (!FieldWraps.INSTANCE.elemental(class_element))
                  for (int index = 0; index < length; ++index) {
                    String string = String.format("%s[%d]", prefix, index);
                    objectFieldVisitor.push(string);
                    visit(string + ".", array[index]);
                    objectFieldVisitor.pop();
                  }
              }
          }
        } catch (Exception exception) {
          exception.printStackTrace();
        }
      }
  }
}
