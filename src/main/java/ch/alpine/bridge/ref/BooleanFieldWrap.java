// code by jph
package ch.alpine.bridge.ref;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;

import ch.alpine.bridge.ref.ann.FieldFuse;

/* package */ class BooleanFieldWrap extends BaseFieldWrap {
  private final FieldFuse fieldFuse;

  public BooleanFieldWrap(Field field) {
    super(field);
    fieldFuse = field.getAnnotation(FieldFuse.class);
  }

  @Override // from FieldWrap
  public Object toValue(String string) {
    return BooleanParser.orNull(string);
  }

  @Override
  public List<Object> options(Object object) {
    return List.of( //
        Boolean.FALSE, //
        Boolean.TRUE);
  }

  @Override // from FieldWrap
  public FieldPanel createFieldPanel(Object object, Object value) {
    if (Objects.isNull(fieldFuse))
      return new BooleanCheckBox(this, (Boolean) value);
    String string = fieldFuse.value();
    return new BooleanButton(this, string.isEmpty() //
        ? getField().getName()
        : string);
  }
}
