package ch.ethz.idsc.tensor.ref;

import java.lang.reflect.Field;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.Tensors;
import ch.ethz.idsc.tensor.io.StringScalarQ;

public class TensorType implements FieldIf {
  @Override
  public boolean isTracking(Class<?> cls) {
    return Tensor.class.equals(cls);
  }

  @Override
  public Object toObject(Class<?> cls, String string) {
    return Tensors.fromString(string);
  }

  @Override
  public String toString(Object object) {
    return object.toString();
  }

  @Override
  public boolean isValidValue(Field field, Object object) {
    return object instanceof Tensor //
        && !StringScalarQ.any((Tensor) object);
  }
}
