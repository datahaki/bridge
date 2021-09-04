// code by jph
package ch.alpine.java.ref.obj;

import ch.alpine.java.ref.FieldWrap;

@FunctionalInterface
public interface ObjectFieldCallback {
  /** @param key typically prefix followed by field name
   * @param fieldWrap
   * @param object that hosts field with value
   * @param value current value may be null */
  void elemental(String key, FieldWrap fieldWrap, Object object, Object value);
}
