// code by jph
package ch.alpine.java.ref;

import java.lang.reflect.Field;

@FunctionalInterface
public interface ObjectFieldVisitor {
  /** invoked before the traversing of a nested element
   * 
   * @param key
   * @param field
   * @param index of entry of array or list, or null */
  default void push(String key, Field field, Integer index) {
    // ---
  }

  /** @param key typically prefix followed by field name
   * @param fieldWrap
   * @param object that hosts field with value
   * @param value current value may be null */
  void accept(String key, FieldWrap fieldWrap, Object object, Object value);

  /** invoked for every push after the traversing of nested element is complete */
  default void pop() {
    // ---
  }
}
