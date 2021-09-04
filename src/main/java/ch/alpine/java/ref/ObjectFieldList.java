// code by jph
package ch.alpine.java.ref;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class ObjectFieldList implements ObjectFieldVisitor {
  public static List<String> of(Object object) {
    ObjectFieldList objectFieldList = new ObjectFieldList();
    ObjectFields.of(object, objectFieldList);
    return objectFieldList.getList();
  }

  // ==================================================
  private final List<String> list = new LinkedList<>();

  private ObjectFieldList() {
    // ---
  }

  @Override
  public void accept(String key, FieldWrap fieldWrap, Object object, Object value) {
    if (Objects.nonNull(value))
      list.add(key + "=" + fieldWrap.toString(value));
  }

  public List<String> getList() {
    return list;
  }
}
