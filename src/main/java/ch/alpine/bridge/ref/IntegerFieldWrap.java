// code by jph
package ch.alpine.bridge.ref;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ch.alpine.bridge.ref.ann.FieldClip;
import ch.alpine.bridge.ref.ann.FieldClips;
import ch.alpine.bridge.ref.ann.FieldSlider;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Scalars;
import ch.alpine.tensor.alg.Range;
import ch.alpine.tensor.chq.FiniteScalarQ;
import ch.alpine.tensor.sca.Clip;

/* package */ final class IntegerFieldWrap extends SelectableFieldWrap {
  /** allow choosing of hours 0,1,...,23 */
  private static final Scalar WIDTH_LIMIT = RealScalar.of(24);
  // ---
  private final FieldClips fieldClips;

  /** @param field
   * @throws Exception if annotations are corrupt */
  public IntegerFieldWrap(Field field) {
    super(field);
    FieldClip fieldClip = field.getAnnotation(FieldClip.class);
    fieldClips = Objects.nonNull(fieldClip) //
        ? FieldClips.wrap(fieldClip)
        : null;
  }

  @Override // from FieldWrap
  public Object toValue(String string) {
    Objects.requireNonNull(string);
    try {
      return Integer.parseInt(string);
    } catch (Exception exception) {
      // ---
    }
    return null;
  }

  @Override // from FieldWrap
  public boolean isValidValue(Object value) {
    Integer scalar = (Integer) Objects.requireNonNull(value);
    // ---
    if (Objects.nonNull(fieldClips) && !fieldClips.test(RealScalar.of(scalar)))
      return false;
    // ---
    return true;
  }

  @Override // from FieldWrap
  public List<Object> options(Object object) {
    List<Object> list = super.options(object);
    if (list.isEmpty() && Objects.nonNull(fieldClips)) {
      if (fieldClips.isFinite()) {
        Clip clip = fieldClips.clip();
        if (Scalars.lessEquals(clip.width(), WIDTH_LIMIT))
          return Range.closed(clip).stream() //
              .map(Scalar.class::cast) //
              .collect(Collectors.toList());
      }
      return Stream.of(fieldClips.min(), fieldClips.max()) //
          .filter(FiniteScalarQ::of) //
          .collect(Collectors.toList());
    }
    return list;
  }

  @Override // from SelectableFieldWrap
  public FieldPanel createFieldPanel(Object object, Object value) {
    Field field = getField();
    FieldSlider fieldSlider = field.getAnnotation(FieldSlider.class);
    return Objects.nonNull(fieldSlider) && fieldClips.isFinite() // slider implies clip non-null
        ? new SliderPanel(this, fieldClips, value, fieldSlider)
        : super.createFieldPanel(object, value);
  }
}
