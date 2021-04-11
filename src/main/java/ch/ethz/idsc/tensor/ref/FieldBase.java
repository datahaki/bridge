package ch.ethz.idsc.tensor.ref;

import java.lang.reflect.Field;

public abstract class FieldBase implements FieldType {
  protected final Field field;

  public FieldBase(Field field) {
    this.field = field;
  }

  @Override
  public final Field getField() {
    return field;
  }
}
