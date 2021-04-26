// code by jph
package ch.ethz.idsc.tensor.ref;

import java.lang.reflect.Field;
import java.util.Objects;

import ch.ethz.idsc.tensor.ref.gui.FieldPanel;

public class StringFieldWrap extends BaseFieldWrap {
  public StringFieldWrap(Field field) {
    super(field);
  }

  @Override // from FieldWrap
  public Object toValue(String string) {
    return Objects.requireNonNull(string);
  }

  @Override // from FieldWrap
  public String toString(Object value) {
    return (String) Objects.requireNonNull(value);
  }

  @Override
  public FieldPanel createFieldPanel(Object value) {
    return new StringPanel(this, value);
  }
}
