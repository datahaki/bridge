// code by jph
package ch.alpine.bridge.ref.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ch.alpine.bridge.lang.ClassHierarchy;
import ch.alpine.bridge.ref.FieldWrap;
import ch.alpine.bridge.ref.FieldWraps;

public class ObjectFields {
  /** @param object may be null
   * @param objectFieldVisitor
   * @throws Exception if any input parameter is null */
  public static void of(Object object, ObjectFieldVisitor objectFieldVisitor) {
    Objects.requireNonNull(objectFieldVisitor);
    if (Objects.nonNull(object)) {
      ReflectionMarkers.INSTANCE.register(object);
      new ObjectFields(objectFieldVisitor).visit("", object);
    }
  }

  // ---
  private final ObjectFieldVisitor objectFieldVisitor;

  private ObjectFields(ObjectFieldVisitor objectFieldVisitor) {
    this.objectFieldVisitor = objectFieldVisitor;
  }

  private void visit(String _prefix, Object object) {
    if (Objects.nonNull(object))
      for (Field field : list(object.getClass())) {
        String prefix = _prefix + field.getName();
        switch (objectFieldVisitor.classify(field)) {
        case NODE -> {
          Class<?> class_field = field.getType();
          if (class_field.isArray())
            iterate(prefix, field, List.of((Object[]) get(field, object)));
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
        case LEAF -> {
          objectFieldVisitor.accept(prefix, FieldWraps.INSTANCE.wrap(field), object, get(field, object));
        }
        default -> {
          // skip
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
      throw new RuntimeException(exception);
    }
  }

  /** @param cls
   * @return list of public fields in hierarchy of given class */
  public static List<Field> list(Class<?> cls) {
    return ClassHierarchy.of(cls) //
        .stream() //
        .map(Class::getDeclaredFields) //
        .flatMap(Stream::of) //
        .collect(Collectors.toList());
  }

  private static List<Object> privateList(Object object) {
    List<Object> list = new ArrayList<>();
    ObjectFields.of(object, new ObjectFieldAll() {
      @Override // from ObjectFieldVisitor
      public void accept(String key, FieldWrap fieldWrap, Object object, Object value) {
        list.add(value);
      }
    });
    return list;
  }

  /** @param object1
   * @param object2
   * @return true if given objects are of the same class and all fields
   * satisfy {@link #equals(Object)} */
  public static boolean deepEquals(Object object1, Object object2) {
    return Objects.nonNull(object1) //
        && Objects.nonNull(object2) //
        && object1.getClass().equals(object2.getClass()) //
        && privateList(object1).equals(privateList(object2));
  }

  /** @param object
   * @return hash code of all field values */
  public static int hash(Object object) {
    return privateList(object).hashCode();
  }
}
