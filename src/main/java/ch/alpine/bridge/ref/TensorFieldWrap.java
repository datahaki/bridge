// code by jph
package ch.alpine.bridge.ref;

import java.lang.reflect.Field;

import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.io.StringScalarQ;

/* package */ class TensorFieldWrap extends SelectableFieldWrap {
  public TensorFieldWrap(Field field) {
    super(field);
  }

  @Override // from FieldWrap
  public Object toValue(String string) {
    return Tensors.fromString(string);
  }

  @Override // from FieldWrap
  public String toString(Object value) {
    return value.toString();
  }

  @Override // from FieldWrap
  public boolean isValidValue(Object value) {
    Tensor tensor = (Tensor) value;
    return !StringScalarQ.any(tensor); // throws exception if value is null
  }
}
