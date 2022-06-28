// code by jph
package ch.alpine.bridge.ref;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Stream;

import ch.alpine.bridge.ref.ann.FieldSelectionCallback;

/* package */ class EnumFieldWrap extends BaseFieldWrap {
  private final Object[] enumConstants;

  public EnumFieldWrap(Field field) {
    super(field);
    enumConstants = Objects.requireNonNull(field.getType().getEnumConstants());
  }

  @Override // from FieldWrap
  public Object toValue(String string) {
    Objects.requireNonNull(string);
    return Stream.of(enumConstants) //
        .map(Enum.class::cast) //
        .filter(object -> object.name().equals(string)) //
        .findFirst() //
        .orElse(null);
  }

  @Override // from FieldWrap
  public String toString(Object object) {
    return ((Enum<?>) object).name();
  }

  @SuppressWarnings("unchecked")
  @Override // from FieldWrap
  public FieldPanel createFieldPanel(Object object, Object value) {
    Field field = getField();
    Supplier<List<Object>> supplier = () -> List.of(enumConstants);
    FieldSelectionCallback fieldSelectionCallback = field.getAnnotation(FieldSelectionCallback.class);
    if (Objects.nonNull(fieldSelectionCallback))
      try {
        Method method = getField().getDeclaringClass().getMethod(fieldSelectionCallback.value());
        supplier = () -> {
          try {
            return (List<Object>) method.invoke(object);
          } catch (Exception exception) {
            exception.printStackTrace();
          }
          return List.of();
        };
      } catch (Exception exception) {
        exception.printStackTrace();
      }
    return new EnumPanel(this, value, supplier);
  }
}
