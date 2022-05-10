// code by jph
package ch.alpine.bridge.ref.util;

import java.lang.reflect.Field;

import ch.alpine.bridge.ref.FieldWrap;

public record FieldValueContainer(String key, FieldWrap fieldWrap, Object object, Object value) {
  /** @return */
  public Field field() {
    return fieldWrap.getField();
  }
}
