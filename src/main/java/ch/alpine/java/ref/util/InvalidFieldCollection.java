// code by jph
package ch.alpine.java.ref.util;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import ch.alpine.java.ref.FieldWrap;
import ch.alpine.java.ref.ObjectFieldVisitor;
import ch.alpine.java.ref.ObjectFields;

public class InvalidFieldCollection implements ObjectFieldVisitor {
  /** @param object
   * @return */
  public static boolean isEmpty(Object object) {
    InvalidFieldCollection invalidFieldCollection = new InvalidFieldCollection();
    ObjectFields.of(object, invalidFieldCollection);
    return invalidFieldCollection.list().isEmpty();
  }

  // ---
  private final List<FieldValueContainer> list = new LinkedList<>();

  @Override
  public void accept(String key, FieldWrap fieldWrap, Object object, Object value) {
    if (!fieldWrap.isValidValue(value))
      list.add(new FieldValueContainer(key, fieldWrap, object, value));
  }

  public List<FieldValueContainer> list() {
    return Collections.unmodifiableList(list);
  }
}
