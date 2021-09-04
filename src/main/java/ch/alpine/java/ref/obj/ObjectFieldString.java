// code by jph
package ch.alpine.java.ref.obj;

import ch.alpine.java.ref.FieldWrap;

public class ObjectFieldString implements ObjectFieldCallback {
  private final StringBuilder stringBuilder = new StringBuilder();

  @Override
  public void elemental(String key, FieldWrap fieldWrap, Object object, Object value) {
    stringBuilder.append(key + "=" + fieldWrap.toString(value) + "\n");
  }

  @Override
  public String toString() {
    return stringBuilder.toString();
  }
}
