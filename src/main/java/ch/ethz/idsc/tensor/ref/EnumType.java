// code by jph
package ch.ethz.idsc.tensor.ref;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.stream.Stream;

import ch.ethz.idsc.tensor.ref.gui.EnumPanel;
import ch.ethz.idsc.tensor.ref.gui.FieldPanel;

public class EnumType extends FieldBase {
  private final Object[] enumConstants;

  public EnumType(Field field) {
    super(field);
    enumConstants = field.getType().getEnumConstants();
  }

  @Override
  public boolean isTracking(Class<?> cls) {
    return Enum.class.isAssignableFrom(cls);
  }

  @Override
  public Object toObject(String string) {
    return Stream.of(enumConstants) //
        .map(Enum.class::cast) //
        .filter(object -> object.name().equals(string)) //
        .findFirst() //
        .orElse(null);
  }

  @Override
  public String toString(Object object) {
    return ((Enum<?>) object).name();
  }

  @Override
  public boolean isValidValue(Object object) {
    return Objects.nonNull(object) //
        && isTracking(object.getClass()); // default implementation
  }

  @Override
  public FieldPanel createFieldPanel(Object value) {
    return new EnumPanel(this, field.getType().getEnumConstants(), value);
  }
}
