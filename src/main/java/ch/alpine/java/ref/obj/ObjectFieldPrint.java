// code by jph
package ch.alpine.java.ref.obj;

public enum ObjectFieldPrint implements ObjectFieldCallback {
  INSTANCE;

  @Override
  public void elemental(String key, Class<?> class_field, Object field_object) {
    System.out.println(key + "=" + field_object);
  }

  @Override
  public void array(String key, Class<?> class_element, Object[] array, int index) {
    System.out.println(key + "=" + array[index]);
  }
}
