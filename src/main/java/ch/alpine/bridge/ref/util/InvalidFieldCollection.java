// code by jph
package ch.alpine.bridge.ref.util;

import java.util.LinkedList;
import java.util.List;

import ch.alpine.bridge.ref.FieldWrap;
import ch.alpine.bridge.ref.ObjectFieldVisitor;
import ch.alpine.bridge.ref.ObjectFields;

public class InvalidFieldCollection implements ObjectFieldVisitor {
  /** @param object
   * @return */
  public static List<FieldValueContainer> of(Object object) {
    InvalidFieldCollection invalidFieldCollection = new InvalidFieldCollection();
    ObjectFields.of(object, invalidFieldCollection);
    return invalidFieldCollection.list();
  }

  /** @param object
   * @return */
  public static boolean isEmpty(Object object) {
    return of(object).isEmpty();
  }

  // ---
  private final List<FieldValueContainer> list = new LinkedList<>();

  @Override
  public void accept(String key, FieldWrap fieldWrap, Object object, Object value) {
    if (!fieldWrap.isValidValue(value))
      list.add(new FieldValueContainer(key, fieldWrap, object, value));
  }

  public List<FieldValueContainer> list() {
    return list;
  }
}