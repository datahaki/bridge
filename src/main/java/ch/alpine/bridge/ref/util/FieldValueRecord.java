// code by jph
package ch.alpine.bridge.ref.util;

import java.lang.reflect.Field;

import ch.alpine.bridge.ref.FieldWrap;

/** only used in {@link InvalidFieldDetection} */
public record FieldValueRecord(String key, FieldWrap fieldWrap, Object object, Object value) {
  /** @return */
  public Field field() {
    return fieldWrap.getField();
  }
}
