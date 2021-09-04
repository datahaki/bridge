// code by jph
package ch.alpine.java.ref;

import java.lang.reflect.Field;

@FunctionalInterface
public interface ObjectFieldVisitor {
  /** @param key
   * @param field */
  default void push(String key, Field field, Integer index) {
    // ---
  }

  /** @param key typically prefix followed by field name
   * @param fieldWrap
   * @param object that hosts field with value
   * @param value current value may be null */
  void accept(String key, FieldWrap fieldWrap, Object object, Object value);

  default void pop() {
    // ---
  }
}
