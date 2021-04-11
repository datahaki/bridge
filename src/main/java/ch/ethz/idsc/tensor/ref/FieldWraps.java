// code by jph
package ch.ethz.idsc.tensor.ref;

import java.awt.Color;
import java.io.File;
import java.lang.reflect.Field;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;

public enum FieldWraps {
  ;
  public static FieldWrap wrap(Field field) {
    Class<?> cls = field.getType();
    // ---
    if (String.class.equals(cls))
      return new StringFieldWrap(field);
    if (Boolean.class.equals(cls))
      return new BooleanFieldWrap(field);
    if (Enum.class.isAssignableFrom(cls))
      return new EnumFieldWrap(field);
    if (Tensor.class.equals(cls))
      return new TensorFieldWrap(field);
    if (Scalar.class.equals(cls))
      return new ScalarFieldWrap(field);
    if (Color.class.equals(cls))
      return new ColorFieldWrap(field);
    if (File.class.equals(cls))
      return new FileFieldWrap(field);
    System.err.println("NO MATCH");
    return null;
  }
}