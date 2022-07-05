// code by jph
package ch.alpine.bridge.ref;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import ch.alpine.bridge.ref.ann.FieldSelectionArray;
import ch.alpine.bridge.ref.ann.FieldSelectionCallback;

/* package */ abstract class SelectableFieldWrap extends BaseFieldWrap {
  private final FieldSelectionArray fieldSelectionArray;
  private final FieldSelectionCallback fieldSelectionCallback;

  public SelectableFieldWrap(Field field) {
    super(field);
    fieldSelectionArray = field.getAnnotation(FieldSelectionArray.class);
    fieldSelectionCallback = field.getAnnotation(FieldSelectionCallback.class);
  }

  @Override // from FieldWrap
  public String toString(Object value) {
    return value.toString();
  }

  @Override
  @SuppressWarnings("unchecked")
  public List<Object> options(Object object) {
    if (Objects.nonNull(fieldSelectionArray))
      return Arrays.stream(fieldSelectionArray.value()).map(this::toValue).toList();
    if (Objects.nonNull(fieldSelectionCallback))
      try {
        Method method = getField().getDeclaringClass().getMethod(fieldSelectionCallback.value());
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
