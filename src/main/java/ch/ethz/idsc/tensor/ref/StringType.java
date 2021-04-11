// code by jph
package ch.ethz.idsc.tensor.ref;

import java.lang.reflect.Field;

import ch.ethz.idsc.tensor.ref.gui.FieldPanel;
import ch.ethz.idsc.tensor.ref.gui.StringPanel;

public class StringType extends FieldBase {
  public StringType(Field field) {
    super(field);
  }

  @Override
  public boolean isTracking(Class<?> cls) {
    return String.class.equals(cls);
  }

  @Override
  public Object toObject(String string) {
    return string;
  }

  @Override
  public String toString(Object object) {
    return object.toString();
  }

  @Override
  public FieldPanel createFieldPanel(Object value) {
    return new StringPanel(this, value);
  }
}
