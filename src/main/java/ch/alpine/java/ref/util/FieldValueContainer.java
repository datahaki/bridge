// code by jph
package ch.alpine.java.ref.util;

import ch.alpine.java.ref.FieldWrap;

public class FieldValueContainer {
  public final String key;
  public final FieldWrap fieldWrap;
  public final Object object;
  public final Object value;

  public FieldValueContainer(String key, FieldWrap fieldWrap, Object object, Object value) {
    this.key = key;
    this.fieldWrap = fieldWrap;
    this.object = object;
    this.value = value;
  }
}
