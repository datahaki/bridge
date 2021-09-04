// code by jph
package ch.alpine.java.ref.obj;

import java.lang.reflect.Field;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;

import ch.alpine.java.ref.FieldTrack;
import ch.alpine.java.ref.FieldWrap;
import ch.alpine.java.ref.FieldWraps;

public class ObjectFieldVisitor {
  public static <T extends ObjectFieldCallback> T of(T objectFieldCallback, Object object) {
    new ObjectFieldVisitor(objectFieldCallback).visit("", object);
    return objectFieldCallback;
  }

  // ---
  private final ObjectFieldCallback objectFieldCallback;

  private ObjectFieldVisitor(ObjectFieldCallback objectFieldCallback) {
    this.objectFieldCallback = objectFieldCallback;
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
                objectFieldCallback.elemental(prefix, fieldWrap, object, field.get(object));
            }
          } else {
            if (FieldTrack.isNested(field))
              if (!class_field.isArray()) {
                objectFieldCallback.push(prefix);
                visit(prefix + ".", field.get(object));
                objectFieldCallback.pop();
              } else {
                Object[] array = (Object[]) field.get(object);
                final int length = array.length;
                Class<?> class_element = class_field.getComponentType();
                if (!FieldWraps.INSTANCE.elemental(class_element))
                  for (int index = 0; index < length; ++index) {
                    String string = String.format("%s[%d]", prefix, index);
                    objectFieldCallback.push(string);
                    visit(string + ".", array[index]);
                    objectFieldCallback.pop();
                  }
              }
          }
        } catch (Exception exception) {
          exception.printStackTrace();
        }
      }
  }
}
