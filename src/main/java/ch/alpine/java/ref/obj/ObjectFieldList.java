// code by jph
package ch.alpine.java.ref.obj;

import java.util.LinkedList;
import java.util.List;

import ch.alpine.java.ref.FieldWrap;

public class ObjectFieldList implements ObjectFieldCallback {
  private final List<String> list = new LinkedList<>();

  @Override
  public void elemental(String key, FieldWrap fieldWrap, Object object, Object value) {
    list.add(key + "=" + fieldWrap.toString(value));
  }

  public List<String> getList() {
    return list;
  }
}
