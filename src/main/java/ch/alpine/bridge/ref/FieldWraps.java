// code by jph
package ch.alpine.bridge.ref;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.io.File;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.sca.Clip;

public enum FieldWraps {
  INSTANCE;

  private final Map<Class<?>, Function<Field, FieldWrap>> map = new HashMap<>();

  FieldWraps() {
    map.put(String.class, StringFieldWrap::new);
    map.put(Boolean.class, BooleanFieldWrap::new);
    map.put(Integer.class, IntegerFieldWrap::new);
    map.put(File.class, FileFieldWrap::new);
    map.put(Color.class, ColorFieldWrap::new);
    map.put(Font.class, FontFieldWrap::new);
    map.put(LocalDate.class, LocalDateFieldWrap::new);
    map.put(LocalTime.class, LocalTimeFieldWrap::new);
    map.put(LocalDateTime.class, LocalDateTimeFieldWrap::new);
    map.put(Rectangle.class, RectangleFieldWrap::new);
    // ---
    map.put(Tensor.class, TensorFieldWrap::new);
    map.put(Scalar.class, ScalarFieldWrap::new);
    map.put(Clip.class, ClipFieldWrap::new);
  }

  /** @param cls
   * @return whether a given type maps to a {@link FieldWrap} */
  public boolean elemental(Class<?> cls) {
    return map.containsKey(cls) //
        || cls.isEnum();
  }

  /** @param cls
   * @param function */
  public void insert(Class<?> cls, Function<Field, FieldWrap> function) {
    if (map.containsKey(cls))
      throw new IllegalArgumentException(cls.getCanonicalName());
    map.put(cls, function);
  }

  /** @param field non-null
   * @return instance of {@link FieldWrap} or null if field type is not supported */
  public FieldWrap wrap(Field field) {
    Class<?> cls = field.getType();
    Function<Field, FieldWrap> function = map.get(cls);
    if (Objects.nonNull(function))
      return function.apply(field);
    if (cls.isEnum()) {
      function = EnumFieldWrap::new;
      map.put(cls, function);
      return function.apply(field);
    }
    return null;
  }
}
