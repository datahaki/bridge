// code by jph
package ch.alpine.bridge.ref;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ch.alpine.bridge.ref.ObjectFieldVisitor.Type;
import ch.alpine.bridge.ref.ann.ReflectionMarker;

public class ObjectFields {
  private static final Set<Class<?>> REMINDER_SET = Collections.synchronizedSet(new HashSet<>());
  static {
    REMINDER_SET.add(Object.class);
  }

  /** @param object may be null
   * @param objectFieldVisitor
   * @throws Exception if any input parameter is null */
  public static void of(Object object, ObjectFieldVisitor objectFieldVisitor) {
    if (Objects.nonNull(object))
      for (Class<?> cls : ClassHierarchy.of(object.getClass())) {
        ReflectionMarker reflectionMarker = cls.getAnnotation(ReflectionMarker.class);
        if (Objects.isNull(reflectionMarker) && REMINDER_SET.add(cls))
          System.err.println("hint: use @ReflectionMarker on " + cls);
      }
    new ObjectFields(Objects.requireNonNull(objectFieldVisitor)).visit("", object);
  }

  // ---
  private final ObjectFieldVisitor objectFieldVisitor;

  private ObjectFields(ObjectFieldVisitor objectFieldVisitor) {
    this.objectFieldVisitor = objectFieldVisitor;
  }

  private void visit(String _prefix, Object object) {
    if (Objects.nonNull(object))
      for (Field field : list(object.getClass())) {
        Type type = objectFieldVisitor.getType(field);
        String prefix = _prefix + field.getName();
        switch (type) {
        case NODE -> {
          Class<?> class_field = field.getType();
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
        case LEAF -> {
          FieldWrap fieldWrap = FieldWraps.INSTANCE.wrap(field);
          if (Objects.nonNull(fieldWrap))
            objectFieldVisitor.accept(prefix, fieldWrap, object, get(field, object));
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
      exception.printStackTrace();
    }
    throw new IllegalArgumentException();
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
    ObjectFields.of(object, new ObjectFieldGui() {
      @Override
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
