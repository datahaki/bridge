// code by jph
package ch.ethz.idsc.tensor.ref;

import java.lang.reflect.Field;
import java.util.Objects;

import ch.ethz.idsc.tensor.IntegerQ;
import ch.ethz.idsc.tensor.Scalar;
import ch.ethz.idsc.tensor.Scalars;
import ch.ethz.idsc.tensor.io.StringScalarQ;
import ch.ethz.idsc.tensor.qty.UnitSystem;
import ch.ethz.idsc.tensor.ref.gui.FieldPanel;
import ch.ethz.idsc.tensor.sca.Clip;

public class ScalarFieldWrap extends BaseFieldWrap {
  private final FieldIntegerQ fieldIntegerQ;
  private final FieldClip fieldClip;

  public ScalarFieldWrap(Field field) {
    super(field);
    fieldIntegerQ = field.getAnnotation(FieldIntegerQ.class);
    fieldClip = field.getAnnotation(FieldClip.class);
  }

  @Override // from FieldWrap
  public Object toValue(String string) {
    return Scalars.fromString(string);
  }

  @Override // from FieldWrap
  public String toString(Object object) {
    return object.toString();
  }

  @Override
  public boolean isValidValue(Object value) {
    Scalar scalar = (Scalar) value;
    if (StringScalarQ.of(scalar))
      return false;
    // ---
    if (Objects.nonNull(fieldIntegerQ))
      if (!IntegerQ.of(scalar))
        return false;
    // ---
    if (Objects.nonNull(fieldClip))
      try {
        Clip clip = TensorReflection.clip(fieldClip);
        if (clip.isOutside(UnitSystem.SI().apply(scalar)))
          return false;
      } catch (Exception exception) {
        // System.err.println("unit incompatible " + clip + " " + scalar);
        return false;
      }
    return true;
  }

  @Override
  public FieldPanel createFieldPanel(Object value) {
    return new StringPanel(this, value);
  }
}
