// code by jph
package ch.ethz.idsc.tensor.ref;

import java.lang.reflect.Field;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.io.StringScalarQ;
import ch.ethz.idsc.tensor.ref.gui.FieldPanel;
import ch.ethz.idsc.tensor.ref.gui.StringPanel;

public class TensorType extends FieldBase {
  public TensorType(Field field) {
    super(field);
  }

  @Override
  public boolean isTracking(Class<?> cls) {
    return Tensor.class.equals(cls);
  }

  @Override
  public Object toObject(String string) {
    return Tensors.fromString(string);
  }

  @Override
  public String toString(Object object) {
    return object.toString();
  }

  @Override
  public boolean isValidValue(Object value) {
    Tensor tensor = (Tensor) value;
    return !StringScalarQ.any((Tensor) value);
  }

  @Override
  public FieldPanel createFieldPanel(Object value) {
    return new StringPanel(this, value);
  }
}
