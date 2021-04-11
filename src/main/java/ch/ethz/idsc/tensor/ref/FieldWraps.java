// code by jph
package ch.ethz.idsc.tensor.ref;

import java.awt.Color;
import java.io.File;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Tensor;

public enum FieldWraps {
  INSTANCE;

  private final Map<Class<?>, Function<Field, FieldWrap>> map = new HashMap<>();

  private FieldWraps() {
    map.put(String.class, StringFieldWrap::new);
    map.put(Boolean.class, BooleanFieldWrap::new);
    map.put(Tensor.class, TensorFieldWrap::new);
    map.put(Scalar.class, ScalarFieldWrap::new);
    map.put(Color.class, ColorFieldWrap::new);
    map.put(File.class, FileFieldWrap::new);
  }

  public FieldWrap wrap(Field field) {
    Class<?> cls = field.getType();
    // ---
    if (Enum.class.isAssignableFrom(cls))
      return new EnumFieldWrap(field);
    // ---
    Function<Field, FieldWrap> function = map.get(cls);
    if (Objects.nonNull(function))
      return function.apply(field);
    // ---
    System.err.println("NO MATCH");
    return null;
  }
}