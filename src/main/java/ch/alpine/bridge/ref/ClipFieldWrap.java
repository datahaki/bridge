// code by jph
package ch.alpine.bridge.ref;

import java.lang.reflect.Field;

import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.sca.Clip;
import ch.alpine.tensor.sca.Clips;

/* package */ class ClipFieldWrap extends SelectableFieldWrap {
  public ClipFieldWrap(Field field) {
    super(field);
  }

  @Override // from FieldWrap
  public Clip toValue(String string) {
    Tensor vector = Tensors.fromString(string); // throws exception if string is null
    if (vector.length() == 2)
      try {
        return Clips.interval(vector.Get(0), vector.Get(1));
      } catch (Exception exception) {
        // ---
      }
    return null;
  }

  @Override // from FieldWrap
  public String toString(Object value) {
    Clip clip = (Clip) value;
    return Tensors.of(clip.min(), clip.max()).toString();
  }
}
