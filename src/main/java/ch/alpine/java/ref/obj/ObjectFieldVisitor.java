// code by jph
package ch.alpine.java.ref.obj;

import java.lang.reflect.Field;
import java.util.Objects;

import ch.alpine.java.ref.FieldTrack;
import ch.alpine.java.ref.FieldWrap;
import ch.alpine.java.ref.FieldWraps;

public class ObjectFieldVisitor {
  public static void of(ObjectFieldCallback objectFieldCallback, Object object) {
    new ObjectFieldVisitor(objectFieldCallback).visit("", object);
  }

  // ---
  private final ObjectFieldCallback objectFieldCallback;

  private ObjectFieldVisitor(ObjectFieldCallback objectFieldCallback) {
    this.objectFieldCallback = objectFieldCallback;
  }

  private void visit(String _prefix, Object object) {
    Class<?> class_object = object.getClass();
    for (Field field : class_object.getDeclaredFields()) {
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
          if (!class_field.isArray()) {
            if (FieldTrack.isArray(field))
              visit(String.format("%s.", prefix), field.get(object));
          } else {
            if (FieldTrack.isArray(field)) {
              Object[] array = (Object[]) field.get(object);
              final int length = array.length;
              Class<?> class_element = class_field.getComponentType();
              if (FieldWraps.INSTANCE.elemental(class_element)) {
                // for (int index = 0; index < length; ++index) {
                // objectFieldCallback.array(String.format("%s[%d]", prefix, index), class_element, array, index);
                // }
              } else {
                for (int index = 0; index < length; ++index)
                  visit(String.format("%s[%d].", prefix, index), array[index]);
              }
            }
          }
        }
      } catch (Exception exception) {
        exception.printStackTrace();
      }
    }
  }
}
