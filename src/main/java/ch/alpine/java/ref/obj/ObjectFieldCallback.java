// code by jph
package ch.alpine.java.ref.obj;

import ch.alpine.java.ref.FieldWrap;

@FunctionalInterface
public interface ObjectFieldCallback {
  /** @param prefix
   * @param class_field
   * @param field_object */
  void elemental(String prefix, FieldWrap fieldWrap, Object object, Object field_object);
}
