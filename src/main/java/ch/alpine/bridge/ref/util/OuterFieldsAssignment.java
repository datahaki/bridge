// code by jph
package ch.alpine.bridge.ref.util;

import java.util.Properties;
import java.util.Random;
import java.util.function.Consumer;

import ch.alpine.bridge.ref.FieldWrap;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalars;
import ch.alpine.tensor.alg.Array;

/** OuterFieldsAssignment creates a complete, or randomized set of
 * assignments of a parameter object. The assignments are based on
 * {@link FieldWrap#options(Object)}.
 * 
 * This is useful for automatic testing of functionality when subject
 * to different assignments of a parameter object.
 * 
 * Remark:
 * If given limit in {@link #randomize(int)} exceeds number of possible
 * combinations, then a complete, systematic enumeration is performed. */
public class OuterFieldsAssignment<T> extends BaseFieldsAssignment<T> {
  /** @param object
   * @param consumer of given object but with fields assigned based on all possible
   * combinations suggested by the field type, and annotations */
  public OuterFieldsAssignment(T object, Consumer<T> consumer) {
    super(object, consumer);
  }

  @Override // from BaseFieldsAssignment
  public void randomize(Random random, int limit) {
    if (Scalars.lessEquals(total, RealScalar.of(limit)))
      forEach();
    else
      super.randomize(random, limit);
  }

  @Override // from BaseFieldsAssignment
  protected void insert(Properties properties, Random random) {
    // ---
  }

  /** Careful: the number of combinations may be large */
  public void forEach() {
    Array.forEach(list -> build(list, RANDOM), array);
  }
}
