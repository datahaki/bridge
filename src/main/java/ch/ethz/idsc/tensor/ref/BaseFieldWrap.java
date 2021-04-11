// code by jph
package ch.ethz.idsc.tensor.ref;

import java.lang.reflect.Field;

public abstract class BaseFieldWrap implements FieldWrap {
  protected final Field field;

  public BaseFieldWrap(Field field) {
    this.field = field;
  }

  @Override
  public final Field getField() {
    return field;
  }

  @Override
  public boolean isValidValue(Object value) {
    return true;
  }
}
