// code by jph
package ch.alpine.java.ref;

@FunctionalInterface
public interface ObjectFieldVisitor {
  /** @param key */
  default void push(String key) {
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
