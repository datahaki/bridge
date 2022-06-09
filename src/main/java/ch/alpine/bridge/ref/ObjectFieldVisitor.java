// code by jph
package ch.alpine.bridge.ref;

import java.lang.reflect.Field;

/** Hint: extend from {@link ObjectFieldGui} or {@link ObjectFieldIo} */
public interface ObjectFieldVisitor {
  public static enum Type {
    /** a node invokes push and pop at a later point */
    NODE,
    /** accept is called for a leaf */
    LEAF,
    /** ignore field */
    SKIP;
  }

  Type getType(Field field);

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
