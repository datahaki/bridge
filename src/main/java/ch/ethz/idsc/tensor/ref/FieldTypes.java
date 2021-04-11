// code by jph
package ch.ethz.idsc.tensor.ref;

import java.awt.Color;
import java.io.File;
import java.lang.reflect.Field;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;

public enum FieldTypes {
  ;
  public static FieldType getFieldType(Field field) {
    Class<?> cls = field.getType();
    if (String.class.equals(cls))
      return new StringType(field);
    if (Boolean.class.equals(cls))
      return new BooleanType(field);
    if (Enum.class.isAssignableFrom(cls))
      return new EnumType(field);
    if (Tensor.class.equals(cls))
      return new TensorType(field);
    if (Scalar.class.equals(cls))
      return new ScalarType(field);
    if (Color.class.equals(cls))
      return new ColorType(field);
    if (File.class.equals(cls))
      return new FileType(field);
    System.err.println("NO MATCH");
    return null;
  }
}