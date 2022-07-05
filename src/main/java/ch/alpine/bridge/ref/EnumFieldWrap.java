// code by jph
package ch.alpine.bridge.ref;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/* package */ class EnumFieldWrap extends SelectableFieldWrap {
  private final Object[] enumConstants;

  public EnumFieldWrap(Field field) {
    super(field);
    enumConstants = Objects.requireNonNull(field.getType().getEnumConstants());
  }

  @Override // from FieldWrap
  public Object toValue(String string) {
    Objects.requireNonNull(string);
    return Arrays.stream(enumConstants) //
        .map(Enum.class::cast) //
        .filter(object -> object.name().equals(string)) //
        .findFirst() //
        .orElse(null);
  }

  @Override // from FieldWrap
  public String toString(Object value) {
    return Enum.class.cast(value).name();
  }

  @Override // from FieldWrap
  public List<Object> options(Object object) {
    List<Object> list = super.options(object);
    return list.isEmpty() //
        ? List.of(enumConstants)
        : list;
  }

  @Override // from FieldWrap
  public FieldPanel createFieldPanel(Object object, Object value) {
    return new EnumPanel(this, value, () -> options(object));
  }
}
