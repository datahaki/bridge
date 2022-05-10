// code by jph
package ch.alpine.bridge.ref;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.stream.Stream;

import ch.alpine.bridge.ref.ann.FieldList;
import ch.alpine.bridge.ref.ann.FieldListType;

/* package */ class EnumFieldWrap extends BaseFieldWrap {
  private final Object[] enumConstants;

  public EnumFieldWrap(Field field) {
    super(field);
    enumConstants = Objects.requireNonNull(field.getType().getEnumConstants());
  }

  @Override // from FieldWrap
  public Object toValue(String string) {
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

  @Override // from FieldWrap
  public FieldPanel createFieldPanel(Object object, Object value) {
    Field field = getField();
    FieldList fieldListType = field.getAnnotation(FieldList.class);
    FieldListType f = Objects.isNull(fieldListType) //
        ? FieldListType.TEXT_FIELD
        : fieldListType.value();
    switch (f) {
    case TEXT_FIELD:
      return new EnumPanel(this, enumConstants, value);
    case LIST:
      return new ListPanel(this, enumConstants, value);
    case RADIO:
      return new RadioPanel(this, enumConstants, value);
    default:
      throw new IllegalArgumentException();
    }
  }
}
