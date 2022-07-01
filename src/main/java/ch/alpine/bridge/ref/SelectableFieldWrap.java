// code by jph
package ch.alpine.bridge.ref;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
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

  @SuppressWarnings("unchecked")
  @Override // from FieldWrap
  public FieldPanel createFieldPanel(Object object, Object value) {
    if (Objects.nonNull(fieldSelectionArray))
      try {
        List<String> list = List.of(fieldSelectionArray.value());
        return new MenuPanel(this, value, () -> list);
      } catch (Exception exception) {
        exception.printStackTrace();
      }
    if (Objects.nonNull(fieldSelectionCallback)) {
      try {
        Method method = getField().getDeclaringClass().getMethod(fieldSelectionCallback.value());
        return new MenuPanel(this, value, () -> {
          try {
            return (List<String>) method.invoke(object);
          } catch (Exception exception) {
            exception.printStackTrace();
          }
          return List.of();
        });
      } catch (Exception exception) {
        exception.printStackTrace();
      }
    }
    return new PlainStringPanel(this, value);
  }
}