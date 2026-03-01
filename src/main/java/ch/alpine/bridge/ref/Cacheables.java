// code by jph
package ch.alpine.bridge.ref;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ch.alpine.bridge.ref.util.ObjectFieldAll;
import ch.alpine.bridge.ref.util.ObjectFields;
import ch.alpine.bridge.ref.util.ObjectProperties;

public class Cacheables {
  @SuppressWarnings("unchecked")
  public static <T> T copy(T object) {
    try {
      Class<?> cls = object.getClass();
      Constructor<?> constructor = cls.getDeclaredConstructor();
      constructor.trySetAccessible();
      Object copy = constructor.newInstance();
      ObjectProperties.part(copy, ObjectProperties.join(object));
      return (T) copy;
    } catch (Exception exception) {
      throw new IllegalArgumentException();
    }
  }

  /** @param object
   * @return hash code of all field values */
  public static int hash(Object object) {
    return object.getClass().hashCode() + privateList(object).hashCode();
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
}
