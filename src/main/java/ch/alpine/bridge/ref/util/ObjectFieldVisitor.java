// code by jph
package ch.alpine.bridge.ref.util;

import java.lang.reflect.Field;

import ch.alpine.bridge.ref.FieldWrap;

public interface ObjectFieldVisitor {
  /** @param field
   * @return */
  boolean isLeaf(Field field);

  /** @param field
   * @return */
  boolean isNode(Field field);

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
