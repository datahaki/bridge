// code by jph
package ch.alpine.bridge.ref.util;

import java.lang.reflect.Field;

import ch.alpine.bridge.ref.FieldWrap;

public class FieldValueContainer {
  private final String key;
  private final FieldWrap fieldWrap;
  private final Object object;
  private final Object value;

  public FieldValueContainer(String key, FieldWrap fieldWrap, Object object, Object value) {
    this.key = key;
    this.fieldWrap = fieldWrap;
    this.object = object;
    this.value = value;
  }

  public String key() {
    return key;
  }

  public FieldWrap fieldWrap() {
    return fieldWrap;
  }

  public Object object() {
    return object;
  }

  public Object value() {
    return value;
  }

  /** @return */
  public Field field() {
    return fieldWrap.getField();
  }
}
