// code by jph
package ch.alpine.bridge.ref.util;

import java.util.Properties;
import java.util.Random;

import ch.alpine.bridge.ref.FieldWrap;

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
public class OuterFieldsAssignment extends FieldsAssignment {
  /** @param object
   * @param runnable of given object but with fields assigned based on all possible
   * combinations suggested by the field type, and annotations */
  public static FieldsAssignment of(Object object, Runnable runnable) {
    return new OuterFieldsAssignment(object, runnable);
  }

  // ---
  private OuterFieldsAssignment(Object object, Runnable runnable) {
    super(object, runnable);
  }

  @Override // from BaseFieldsAssignment
  protected void insert(Properties properties, Random random) {
    // ---
  }

  @Override // from BaseFieldsAssignment
  protected boolean isGrid() {
    return true;
  }
}
