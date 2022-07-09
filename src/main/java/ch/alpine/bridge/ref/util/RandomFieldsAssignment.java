// code by jph
package ch.alpine.bridge.ref.util;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Random;
import java.util.function.Function;

import ch.alpine.bridge.ref.FieldWrap;
import ch.alpine.bridge.ref.ann.FieldClip;

/** RandomFieldsAssignment creates a randomized set of assignments of
 * a parameter object. The assignments are based on {@link FieldWrap#options(Object)}
 * as well as uniform distributions over the interval {@link FieldClip}.
 * 
 * This is useful for automatic testing of functionality when subject
 * to different assignments of a parameter object. */
public class RandomFieldsAssignment extends FieldsAssignment {
  /** @param object
   * @param runnable */
  public static FieldsAssignment of(Object object, Runnable runnable) {
    return new RandomFieldsAssignment(object, runnable);
  }

  // ---
  private final Map<String, Function<Random, String>> distributions;

  private RandomFieldsAssignment(Object object, Runnable runnable) {
    super(object, runnable);
    distributions = fieldOptionsCollector.distributions();
  }

  @Override // from BaseFieldsAssignment
  protected void insert(Properties properties, Random random) {
    for (Entry<String, Function<Random, String>> entry : distributions.entrySet())
      // mechanism of Properties requires Strings as values, therefore toString()
      properties.put(entry.getKey(), entry.getValue().apply(random));
  }

  @Override
  protected boolean isGrid() {
    return distributions.isEmpty();
  }
}
