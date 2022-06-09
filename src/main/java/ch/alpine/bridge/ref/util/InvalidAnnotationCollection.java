// code by jph
package ch.alpine.bridge.ref.util;

import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import ch.alpine.bridge.ref.FieldWrap;
import ch.alpine.bridge.ref.ObjectFieldIo;
import ch.alpine.bridge.ref.ObjectFields;
import ch.alpine.bridge.ref.ann.FieldSelectionCallback;

public class InvalidAnnotationCollection extends ObjectFieldIo {
  /** @param object
   * @return */
  public static List<FieldValueContainer> of(Object object) {
    InvalidAnnotationCollection invalidAnnotationCollection = new InvalidAnnotationCollection();
    ObjectFields.of(object, invalidAnnotationCollection);
    return invalidAnnotationCollection.list();
  }

  /** @param object
   * @return */
  public static boolean isEmpty(Object object) {
    return of(object).isEmpty();
  }

  // ---
  private final List<FieldValueContainer> list = new LinkedList<>();

  @SuppressWarnings({ "unchecked" })
  @Override
  public void accept(String key, FieldWrap fieldWrap, Object object, Object value) {
    FieldSelectionCallback fieldSelectionCallback = //
        fieldWrap.getField().getAnnotation(FieldSelectionCallback.class);
    if (Objects.nonNull(fieldSelectionCallback))
      try {
        Method method = object.getClass().getMethod(fieldSelectionCallback.value());
        List<String> list = (List<String>) method.invoke(object);
        list.toString();
      } catch (Exception exception) {
        list.add(new FieldValueContainer(key, fieldWrap, object, value));
      }
  }

  public List<FieldValueContainer> list() {
    return list;
  }
}
