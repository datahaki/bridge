// code by jph
package ch.alpine.java.ref;

import java.lang.reflect.Field;
import java.util.Objects;

import ch.alpine.java.ref.gui.FieldPanel;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.io.StringScalarQ;

public class TensorFieldWrap extends BaseFieldWrap {
  private final FieldSelection fieldSelection;

  public TensorFieldWrap(Field field) {
    super(field);
    fieldSelection = field.getAnnotation(FieldSelection.class);
  }

  @Override // from FieldWrap
  public Object toValue(String string) {
    return Tensors.fromString(string);
  }

  @Override // from FieldWrap
  public String toString(Object object) {
    return object.toString();
  }

  @Override
  public boolean isValidValue(Object value) {
    Tensor tensor = (Tensor) value;
    return !StringScalarQ.any(tensor);
  }

  @Override
  public final FieldPanel createFieldPanel(Object value) {
    if (Objects.nonNull(fieldSelection))
      try {
        Tensor tensor = Tensors.fromString(fieldSelection.list());
        return new MenuPanel(this, value, tensor);
      } catch (Exception exception) {
        exception.printStackTrace();
      }
    return new StringPanel(this, value);
  }
}
