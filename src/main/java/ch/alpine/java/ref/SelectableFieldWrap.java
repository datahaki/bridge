// code by jph
package ch.alpine.java.ref;

import java.lang.reflect.Field;
import java.util.Objects;

import ch.alpine.java.ref.gui.FieldPanel;

public abstract class SelectableFieldWrap extends BaseFieldWrap {
  private final FieldSelection fieldSelection;

  public SelectableFieldWrap(Field field) {
    super(field);
    fieldSelection = field.getAnnotation(FieldSelection.class);
  }
  @Override // from FieldWrap
  public final String toString(Object object) {
    return object.toString();
  }


  @Override
  public final FieldPanel createFieldPanel(Object value) {
    if (Objects.nonNull(fieldSelection))
      try {
        return new MenuPanel(this, value, fieldSelection.list().split("\\|"));
      } catch (Exception exception) {
        exception.printStackTrace();
      }
    return new StringPanel(this, value);
  }
}
