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
import ch.alpine.tensor.chq.FiniteScalarQ;
import ch.alpine.tensor.io.StringScalar;
import ch.alpine.tensor.sca.Clip;

/* package */ final class ScalarFieldWrap extends TensorFieldWrap {
  private final FieldInteger fieldIntegerQ;
  private ClipCheck clipCheck = null;

  /** @param field
   * @throws Exception if annotations are corrupt */
  public ScalarFieldWrap(Field field) {
    super(field);
    fieldIntegerQ = field.getAnnotation(FieldInteger.class);
    FieldClip fieldClip = field.getAnnotation(FieldClip.class);
    if (Objects.nonNull(fieldClip))
      clipCheck = new ClipCheck(FieldClips.of(fieldClip));
  }

  @Override // from FieldWrap
  public Object toValue(String string) {
    return Scalars.fromString(string);
  }

  @Override // from FieldWrap
  public boolean isValidValue(Object value) {
    Scalar scalar = (Scalar) Objects.requireNonNull(value);
    if (scalar instanceof StringScalar)
      return false;
    // ---
    if (Objects.nonNull(fieldIntegerQ) && !IntegerQ.of(scalar))
      return false;
    // ---
    if (Objects.nonNull(clipCheck) && !clipCheck.test(scalar))
      return false;
    // ---
    return true;
  }

  @Override // from SelectableFieldWrap
  public FieldPanel createFieldPanel(Object object, Object value) {
    Field field = getField();
    FieldSlider fieldSlider = field.getAnnotation(FieldSlider.class);
    if (Objects.nonNull(fieldSlider)) {
      // clipCheck is expected to be non-null here, otherwise exception
      Clip clip = clipCheck.clip();
      if (FiniteScalarQ.of(clip.width()))
        return new SliderPanel(this, clip, value, fieldSlider);
    }
    return super.createFieldPanel(object, value);
  }
}
