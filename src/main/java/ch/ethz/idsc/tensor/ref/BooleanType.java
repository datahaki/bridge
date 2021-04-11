// code by jph
package ch.ethz.idsc.tensor.ref;

import java.lang.reflect.Field;

import ch.ethz.idsc.tensor.ref.gui.BooleanPanel;
import ch.ethz.idsc.tensor.ref.gui.FieldPanel;

public class BooleanType extends FieldBase {
  public BooleanType(Field field) {
    super(field);
  }

  @Override
  public boolean isTracking(Class<?> cls) {
    return Boolean.class.equals(cls);
  }

  @Override
  public Object toObject(String string) {
    return BooleanParser.orNull(string);
  }

  @Override
  public String toString(Object object) {
    return object.toString();
  }

  @Override
  public FieldPanel createFieldPanel(Object value) {
    return new BooleanPanel((Boolean) value);
  }
}
