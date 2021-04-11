// code by jph
package ch.ethz.idsc.tensor.ref;

import java.lang.reflect.Field;

import ch.ethz.idsc.tensor.ref.gui.FieldPanel;

public class BooleanFieldWrap extends BaseFieldWrap {
  public BooleanFieldWrap(Field field) {
    super(field);
  }

  @Override
  public Object toValue(String string) {
    return BooleanParser.orNull(string);
  }

  @Override
  public String toString(Object object) {
    return object.toString();
  }

  @Override
  public FieldPanel createFieldPanel(Object value) {
    return new BooleanPanel(this, (Boolean) value);
  }
}
