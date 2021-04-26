// code by jph
package ch.ethz.idsc.tensor.ref;

import java.lang.reflect.Field;
import java.util.Objects;

public abstract class BaseFieldWrap implements FieldWrap {
  protected final Field field;

  public BaseFieldWrap(Field field) {
    this.field = Objects.requireNonNull(field);
  }

  @Override // from FieldWrap
  public final Field getField() {
    return field;
  }

  @Override // from FieldWrap
  public boolean isValidValue(Object value) {
    return true;
  }
}
