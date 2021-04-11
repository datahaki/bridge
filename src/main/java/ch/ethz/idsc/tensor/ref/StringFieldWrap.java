// code by jph
package ch.ethz.idsc.tensor.ref;

import java.lang.reflect.Field;

import ch.ethz.idsc.tensor.ref.gui.FieldPanel;

public class StringFieldWrap extends BaseFieldWrap {
  public StringFieldWrap(Field field) {
    super(field);
  }

  @Override
  public Object toValue(String string) {
    return string;
  }

  @Override
  public String toString(Object value) {
    return value.toString();
  }

  @Override
  public FieldPanel createFieldPanel(Object value) {
    return new StringPanel(this, value);
  }
}
