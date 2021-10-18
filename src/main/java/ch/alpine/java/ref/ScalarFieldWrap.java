// code by jph
package ch.alpine.java.ref;

import java.lang.reflect.Field;
import java.util.Objects;

import ch.alpine.java.ref.ann.FieldClip;
import ch.alpine.java.ref.ann.FieldClips;
import ch.alpine.java.ref.ann.FieldInteger;
import ch.alpine.java.ref.ann.FieldSlider;
import ch.alpine.tensor.IntegerQ;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Scalars;
import ch.alpine.tensor.io.StringScalarQ;
import ch.alpine.tensor.qty.UnitSystem;
import ch.alpine.tensor.sca.Clip;

public class ScalarFieldWrap extends TensorFieldWrap {
  private final FieldInteger fieldIntegerQ;
  private Clip clip = null;

  /** @param field
   * @throws Exception if annotations are corrupt */
  public ScalarFieldWrap(Field field) {
    super(field);
    fieldIntegerQ = field.getAnnotation(FieldInteger.class);
    FieldClip fieldClip = field.getAnnotation(FieldClip.class);
    if (Objects.nonNull(fieldClip))
      clip = FieldClips.of(fieldClip);
  }

  @Override // from FieldWrap
  public Object toValue(String string) {
    return Scalars.fromString(string);
  }

  @Override // from FieldWrap
  public boolean isValidValue(Object value) {
    Scalar scalar = (Scalar) value;
    if (StringScalarQ.of(scalar))
      return false;
    // ---
    if (Objects.nonNull(fieldIntegerQ))
      if (!IntegerQ.of(scalar))
        return false;
    // ---
    if (Objects.nonNull(clip))
      try { // throws exception if units are incompatible
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
    Field field = getField();
    FieldClip fieldClip = field.getAnnotation(FieldClip.class);
    if (Objects.nonNull(fieldClip) && Objects.nonNull(field.getAnnotation(FieldSlider.class)))
      try {
        return new SliderPanel(this, fieldClip, value);
      } catch (Exception exception) {
        // ---
      }
    return super.createFieldPanel(value);
  }
}
