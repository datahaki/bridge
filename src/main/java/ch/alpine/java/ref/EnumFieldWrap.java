// code by jph
package ch.alpine.java.ref;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.stream.Stream;

import ch.alpine.java.ref.ann.FieldListed;

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
    FieldListed fieldClip = field.getAnnotation(FieldListed.class);
    // TODO JAVA API
    if (Objects.nonNull(fieldClip))
      // return new RadioPanel(this, enumConstants, value);
      return new ListPanel(this, enumConstants, value);
    // return super.createFieldPanel(object, value);
    return new EnumPanel(this, enumConstants, value);
  }
}
