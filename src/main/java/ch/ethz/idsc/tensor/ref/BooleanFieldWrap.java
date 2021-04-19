// code by jph
package ch.ethz.idsc.tensor.ref;

import java.lang.reflect.Field;
import java.util.Objects;

import ch.ethz.idsc.tensor.ref.gui.FieldPanel;

public class BooleanFieldWrap extends BaseFieldWrap {
  private final FieldFuse fieldFuse;

  public BooleanFieldWrap(Field field) {
    super(field);
    fieldFuse = field.getAnnotation(FieldFuse.class);
  }

  @Override // from FieldWrap
  public Object toValue(String string) {
    return BooleanParser.orNull(string);
  }

  @Override // from FieldWrap
  public String toString(Object object) {
    return object.toString();
  }

  @Override // from FieldWrap
  public FieldPanel createFieldPanel(Object value) {
    return Objects.isNull(fieldFuse) //
        ? new BooleanPanel(this, (Boolean) value)
        : new BooleanButton(this, fieldFuse.text());
  }
}
