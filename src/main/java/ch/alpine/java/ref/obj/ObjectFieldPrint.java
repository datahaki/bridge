// code by jph
package ch.alpine.java.ref.obj;

import ch.alpine.java.ref.FieldWrap;

public class ObjectFieldPrint implements ObjectFieldCallback {
  private final StringBuilder stringBuilder = new StringBuilder();

  @Override
  public void elemental(String key, FieldWrap fieldWrap, Object object, Object field_object) {
    stringBuilder.append(key + "=" + field_object + "\n");
  }

  @Override
  public String toString() {
    return stringBuilder.toString();
  }
}
