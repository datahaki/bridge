// code by jph
package ch.alpine.java.ref;

import java.lang.reflect.Field;

import ch.alpine.java.ref.gui.FieldPanel;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.io.StringScalarQ;

public class TensorFieldWrap extends BaseFieldWrap {
  public TensorFieldWrap(Field field) {
    super(field);
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
  public FieldPanel createFieldPanel(Object value) {
    return new StringPanel(this, value);
  }
}
