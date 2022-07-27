// code by jph
package ch.alpine.bridge.ref;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ch.alpine.bridge.ref.ann.FieldClip;
import ch.alpine.bridge.ref.ann.FieldClips;
import ch.alpine.bridge.ref.ann.FieldInteger;
import ch.alpine.bridge.ref.ann.FieldSlider;
import ch.alpine.tensor.IntegerQ;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Scalars;
import ch.alpine.tensor.alg.Range;
import ch.alpine.tensor.chq.FiniteScalarQ;
import ch.alpine.tensor.io.StringScalar;
import ch.alpine.tensor.sca.Clip;

/* package */ final class ScalarFieldWrap extends TensorFieldWrap {
  /** allow choosing of hours 0,1,...,23 */
  private static final Scalar WIDTH_LIMIT = RealScalar.of(24);
  // ---
  private final FieldInteger fieldInteger;
  private final FieldClips fieldClips;

  /** @param field
   * @throws Exception if annotations are corrupt */
  public ScalarFieldWrap(Field field) {
    super(field);
    fieldInteger = field.getAnnotation(FieldInteger.class);
    FieldClip fieldClip = field.getAnnotation(FieldClip.class);
    fieldClips = Objects.nonNull(fieldClip) //
        ? FieldClips.wrap(fieldClip)
        : null;
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
    if (Objects.nonNull(fieldInteger) && !IntegerQ.of(scalar))
      return false;
    // ---
    if (Objects.nonNull(fieldClips) && !fieldClips.test(scalar))
      return false;
    // ---
    return true;
  }

  @Override // from FieldWrap
  public List<Object> options(Object object) {
    List<Object> list = super.options(object);
    if (list.isEmpty() && Objects.nonNull(fieldClips)) {
      if (Objects.nonNull(fieldInteger) && fieldClips.isFinite()) {
        Clip clip = fieldClips.clip();
        if (Scalars.lessEquals(clip.width(), WIDTH_LIMIT)) {
          return Range.of( //
              Scalars.intValueExact(clip.min()), //
              Scalars.intValueExact(clip.max().add(RealScalar.ONE))).stream() //
              .map(Scalar.class::cast) //
              .collect(Collectors.toList());
        }
      }
      return Stream.of(fieldClips.min(), fieldClips.max()) //
          .filter(FiniteScalarQ::of) //
          .map(this::toString) //
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
