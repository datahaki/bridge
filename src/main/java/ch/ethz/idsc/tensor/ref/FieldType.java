// code by jph
package ch.ethz.idsc.tensor.ref;

import java.lang.reflect.Field;

import ch.ethz.idsc.tensor.ref.gui.FieldPanel;

public interface FieldType {
  Field getField();

  boolean isTracking(Class<?> cls);

  Object toObject(String string);

  String toString(Object value);

  boolean isValidValue(Object value);

  FieldPanel createFieldPanel(Object value);
}
