// code by jph
package ch.alpine.bridge.ref;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import ch.alpine.bridge.ref.ann.FieldClip;
import ch.alpine.bridge.ref.ann.FieldClips;
import ch.alpine.bridge.ref.ann.FieldInteger;
import ch.alpine.bridge.ref.ann.FieldSlider;
import ch.alpine.tensor.IntegerQ;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Scalars;
import ch.alpine.tensor.alg.Range;
import ch.alpine.tensor.io.StringScalar;

/* package */ final class ScalarFieldWrap extends TensorFieldWrap {
  private final FieldInteger fieldInteger;
  private FieldClips fieldClips = null;

  /** @param field
   * @throws Exception if annotations are corrupt */
  public ScalarFieldWrap(Field field) {
    super(field);
    fieldInteger = field.getAnnotation(FieldInteger.class);
    FieldClip fieldClip = field.getAnnotation(FieldClip.class);
    if (Objects.nonNull(fieldClip))
      fieldClips = FieldClips.wrap(fieldClip);
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
    if (list.isEmpty())
      if (Objects.nonNull(fieldInteger) && Objects.nonNull(fieldClips) && fieldClips.isFinite()) {
        Scalar min = fieldClips.min();
        Scalar max = fieldClips.max();
        return Range.of(Scalars.longValueExact(min), Scalars.longValueExact(max) + 1).stream() //
            .map(Scalar.class::cast).collect(Collectors.toList());
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
