// code by jph
package ch.alpine.java.ref.obj;

public interface ObjectFieldCallback {
  void elemental(String prefix, Class<?> class_field, Object field_object);

  void array(String format, Class<?> class_element, Object[] array, int index);
}
