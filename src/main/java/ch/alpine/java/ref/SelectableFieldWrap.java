// code by jph
package ch.alpine.java.ref;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import ch.alpine.java.ref.ann.FieldSelectionArray;
import ch.alpine.java.ref.ann.FieldSelectionCallback;

/* package */ abstract class SelectableFieldWrap extends BaseFieldWrap {
  private final FieldSelectionArray fieldSelection;
  private final FieldSelectionCallback fieldSelectionCallback;

  public SelectableFieldWrap(Field field) {
    super(field);
    fieldSelection = field.getAnnotation(FieldSelectionArray.class);
    fieldSelectionCallback = field.getAnnotation(FieldSelectionCallback.class);
  }

  @Override // from FieldWrap
  public String toString(Object object) {
    return object.toString();
  }

  @SuppressWarnings("unchecked")
  @Override // from FieldWrap
  public FieldPanel createFieldPanel(Object object, Object value) {
    if (Objects.nonNull(fieldSelection))
      try {
        return new MenuPanel(this, value, () -> Arrays.asList(fieldSelection.values()));
      } catch (Exception exception) {
        exception.printStackTrace();
      }
    if (Objects.nonNull(fieldSelectionCallback)) {
      try {
        Method method = getField().getDeclaringClass().getMethod(fieldSelectionCallback.method());
        return new MenuPanel(this, value, () -> {
          try {
            return (List<String>) method.invoke(object);
          } catch (Exception exception) {
            exception.printStackTrace();
          }
          return Arrays.asList();
        });
      } catch (Exception exception) {
        exception.printStackTrace();
      }
    }
    return new StringPanel(this, value);
  }
}
