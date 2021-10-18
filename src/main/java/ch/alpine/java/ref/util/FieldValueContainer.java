// code by jph
package ch.alpine.java.ref.util;

import java.lang.reflect.Field;

import ch.alpine.java.ref.FieldWrap;

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

  public String getKey() {
    return key;
  }

  public Field getField() {
    return getFieldWrap().getField();
  }

  public FieldWrap getFieldWrap() {
    return fieldWrap;
  }

  public Object getObject() {
    return object;
  }

  public Object getValue() {
    return value;
  }
}
