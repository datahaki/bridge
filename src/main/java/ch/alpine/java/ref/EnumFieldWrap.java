// code by jph
package ch.alpine.java.ref;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.stream.Stream;

import ch.alpine.java.ref.ann.FieldList;
import ch.alpine.java.ref.ann.FieldListType;

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
    return switch (f) {
    case TEXT_FIELD -> new EnumPanel(this, enumConstants, value);
    case LIST -> new ListPanel(this, enumConstants, value);
    case RADIO -> new RadioPanel(this, enumConstants, value);
    };
  }
}
