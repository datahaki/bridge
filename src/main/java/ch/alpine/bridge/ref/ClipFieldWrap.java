// code by jph
package ch.alpine.bridge.ref;

import java.lang.reflect.Field;
import java.util.Objects;

import ch.alpine.bridge.ref.ann.FieldClip;
import ch.alpine.bridge.ref.ann.FieldClips;
import ch.alpine.bridge.ref.ann.FieldSlider;
import ch.alpine.tensor.IntegerQ;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Scalars;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.alg.VectorQ;
import ch.alpine.tensor.sca.Clip;
import ch.alpine.tensor.sca.Clips;

/* package */ class ClipFieldWrap extends SelectableFieldWrap {
  private final FieldClips fieldClips;

  public ClipFieldWrap(Field field) {
    super(field);
    FieldClip fieldClip = field.getAnnotation(FieldClip.class);
    fieldClips = Objects.nonNull(fieldClip) //
        ? FieldClips.wrap(fieldClip)
        : null;
  }

  @Override // from FieldWrap
  public Clip toValue(String string) {
    Tensor vector = Tensors.fromString(string); // throws exception if string is null
    if (VectorQ.ofLength(vector, 2)) {
      Scalar min = vector.Get(0);
      Scalar max = vector.Get(1);
      try {
        if (Scalars.lessEquals(min, max))
          return Clips.interval(min, max);
      } catch (Exception exception) {
        // ---
      }
    }
    return null;
  }

  @Override // from FieldWrap
  public boolean isValidValue(Object value) {
    Clip clip = (Clip) Objects.requireNonNull(value);
    boolean valid = true;
    if (Objects.nonNull(fieldClips)) {
      valid &= fieldClips.test(clip.min());
      valid &= fieldClips.test(clip.max());
      if (fieldClips.isInteger())
        valid &= IntegerQ.of(clip.min()) && IntegerQ.of(clip.max());
    }
    return valid;
  }

  @Override // from FieldWrap
  public String toString(Object value) {
    Clip clip = (Clip) value;
    return Tensors.of(clip.min(), clip.max()).toString();
  }

  @Override // from SelectableFieldWrap
  public FieldPanel createFieldPanel(Object object, Object value) {
    Field field = getField();
    FieldSlider fieldSlider = field.getAnnotation(FieldSlider.class);
    return Objects.nonNull(fieldSlider) && fieldClips.isFinite() // slider implies clip non-null
        ? new RangePanel(this, fieldClips, value, fieldSlider)
        : super.createFieldPanel(object, value);
  }
}
