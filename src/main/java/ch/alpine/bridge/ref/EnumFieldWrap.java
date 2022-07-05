// code by jph
package ch.alpine.bridge.ref;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import ch.alpine.bridge.ref.ann.FieldSelectionCallback;

/* package */ class EnumFieldWrap extends BaseFieldWrap {
  private final Object[] enumConstants;
  private final FieldSelectionCallback fieldSelectionCallback;

  public EnumFieldWrap(Field field) {
    super(field);
    enumConstants = Objects.requireNonNull(field.getType().getEnumConstants());
    fieldSelectionCallback = field.getAnnotation(FieldSelectionCallback.class);
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

  @Override
  @SuppressWarnings("unchecked")
  public List<Object> options(Object object) {
    if (Objects.nonNull(fieldSelectionCallback))
      try {
        Method method = getField().getDeclaringClass().getMethod(fieldSelectionCallback.value());
        try {
          return (List<Object>) method.invoke(object);
        } catch (Exception exception) {
          throw new RuntimeException(exception);
        }
      } catch (Exception exception) {
        throw new RuntimeException(exception);
      }
    return List.of(enumConstants); //
  }

  @Override // from FieldWrap
  public FieldPanel createFieldPanel(Object object, Object value) {
    return new EnumPanel(this, value, () -> options(object));
  }
}
