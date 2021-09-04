// code by jph
package ch.alpine.java.ref.obj;

import ch.alpine.java.ref.FieldWrap;

public enum ObjectFieldPrint implements ObjectFieldCallback {
  INSTANCE;

  @Override
  public void elemental(String key, FieldWrap fieldWrap, Object object, Object field_object) {
    System.out.println(key + "=" + field_object);
  }
}
