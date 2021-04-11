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
import ch.ethz.idsc.tensor.ref.gui.StringPanel;
import ch.ethz.idsc.tensor.sca.Clip;

public class ScalarType extends FieldBase {
  public ScalarType(Field field) {
    super(field);
  }

  @Override
  public Object toValue(String string) {
    return Scalars.fromString(string);
  }

  @Override
  public String toString(Object object) {
    return object.toString();
  }

  @Override
  public boolean isValidValue(Object value) {
    Scalar scalar = (Scalar) value;
    if (StringScalarQ.of(scalar))
      return false;
    {
      FieldIntegerQ fieldIntegerQ = field.getAnnotation(FieldIntegerQ.class);
      if (Objects.nonNull(fieldIntegerQ))
        if (!IntegerQ.of(scalar))
          return false;
    }
    {
      FieldClip fieldClip = field.getAnnotation(FieldClip.class);
      if (Objects.nonNull(fieldClip))
        try {
          Clip clip = TensorReflection.clip(fieldClip);
          if (clip.isOutside(UnitSystem.SI().apply(scalar)))
            return false;
        } catch (Exception exception) {
          // System.err.println("unit incompatible " + clip + " " + scalar);
          return false;
        }
    }
    return true;
  }

  @Override
  public FieldPanel createFieldPanel(Object value) {
    return new StringPanel(this, value);
  }
}
