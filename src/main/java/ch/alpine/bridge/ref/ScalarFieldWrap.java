// code by jph
package ch.alpine.bridge.ref;

import java.lang.reflect.Field;
import java.util.Objects;

import ch.alpine.bridge.ref.ann.FieldClip;
import ch.alpine.bridge.ref.ann.FieldClips;
import ch.alpine.bridge.ref.ann.FieldInteger;
import ch.alpine.bridge.ref.ann.FieldSlider;
import ch.alpine.tensor.IntegerQ;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Scalars;
import ch.alpine.tensor.io.StringScalar;
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
    if (scalar instanceof StringScalar)
      return false;
    // ---
    if (Objects.nonNull(fieldIntegerQ) && !IntegerQ.of(scalar))
      return false;
    // ---
    if (Objects.nonNull(clip))
      try { // throws exception if units are incompatible
        if (clip.isOutside(UnitSystem.SI().apply(scalar)))
          return false;
      } catch (Exception exception) {
        return false;
      }
    return true;
  }

  @Override
  public FieldPanel createFieldPanel(Object object, Object value) {
    Field field = getField();
    FieldClip fieldClip = field.getAnnotation(FieldClip.class);
    if (Objects.nonNull(fieldClip)) {
      FieldSlider fieldSlider = field.getAnnotation(FieldSlider.class);
      if (Objects.nonNull(fieldSlider))
        return new SliderPanel(this, fieldClip, value, fieldSlider.showValue(), fieldSlider.showRange());
    }
    return super.createFieldPanel(object, value);
  }
}
