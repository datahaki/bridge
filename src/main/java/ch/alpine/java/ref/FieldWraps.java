// code by jph
package ch.alpine.java.ref;

import java.awt.Color;
import java.io.File;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;

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

  /** @param cls
   * @param function */
  public void insert(Class<?> cls, Function<Field, FieldWrap> function) {
    if (map.containsKey(cls))
      throw new RuntimeException();
    map.put(cls, function);
  }

  /** @param field
   * @return instance of {@link FieldWrap} or null if field type is not supported */
  public FieldWrap wrap(Field field) {
    Class<?> cls = field.getType();
    // ---
    if (cls.isEnum())
      return new EnumFieldWrap(field);
    // ---
    Function<Field, FieldWrap> function = map.get(cls);
    if (Objects.nonNull(function))
      return function.apply(field);
    // ---
    // if (cls.isArray()) {
    // System.out.println(cls.getComponentType());
    // }
    return null;
  }
}