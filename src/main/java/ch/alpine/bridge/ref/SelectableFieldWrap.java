// code by jph
package ch.alpine.bridge.ref;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ch.alpine.bridge.ref.ann.FieldSelectionArray;
import ch.alpine.bridge.ref.ann.FieldSelectionCallback;

abstract class SelectableFieldWrap extends BaseFieldWrap {
  private final FieldSelectionArray fieldSelectionArray;
  private final FieldSelectionCallback fieldSelectionCallback;

  protected SelectableFieldWrap(Field field) {
    super(field);
    fieldSelectionArray = field.getAnnotation(FieldSelectionArray.class);
    fieldSelectionCallback = field.getAnnotation(FieldSelectionCallback.class);
  }

  @Override
  @SuppressWarnings("unchecked")
  public List<Object> options(Object object) {
    if (Objects.nonNull(fieldSelectionArray)) {
      String[] strings = fieldSelectionArray.value();
      List<Object> list = new ArrayList<>(strings.length);
      for (String string : strings) {
        Object value = toValue(string);
        if (Objects.isNull(value))
          // Exception is thrown when a string expression in
          // FieldSelectioArray cannot be converted to a value
          throw new IllegalArgumentException(object + " " + string);
        list.add(value);
      }
      return list;
    }
    if (Objects.nonNull(fieldSelectionCallback))
      try {
        Method method = getField().getDeclaringClass().getMethod(fieldSelectionCallback.value());
        method.trySetAccessible();
        try {
          return (List<Object>) method.invoke(object);
        } catch (Exception exception) {
          throw new RuntimeException(exception);
        }
      } catch (Exception exception) {
        throw new RuntimeException(exception);
      }
    return List.of();
  }

  @Override // from FieldWrap
  public FieldPanel createFieldPanel(Object object, Object value) {
    List<Object> list = options(object);
    if (Objects.nonNull(fieldSelectionArray) || //
        Objects.nonNull(fieldSelectionCallback) || //
        !list.isEmpty())
      return new MenuPanel(this, value, () -> options(object));
    return new PlainStringPanel(this, value);
  }
}
