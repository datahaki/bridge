// code by jph
package ch.alpine.bridge.ref.util;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.function.Function;
import java.util.random.RandomGenerator;

import ch.alpine.bridge.ref.FieldWrap;
import ch.alpine.bridge.ref.ann.FieldClip;

/** RandomFieldsAssignment creates a randomized set of assignments of
 * a parameter object. The assignments are based on {@link FieldWrap#options(Object)}
 * as well as uniform distributions over the interval {@link FieldClip}.
 * 
 * This is useful for automatic testing of functionality when subject
 * to different assignments of a parameter object. */
public class RandomFieldsAssignment extends FieldsAssignment {
  /** @param object */
  public static FieldsAssignment of(Object object) {
    return new RandomFieldsAssignment(object);
  }

  // ---
  private final Map<String, Function<RandomGenerator, String>> distributions;

  private RandomFieldsAssignment(Object object) {
    super(object);
    distributions = fieldOptionsCollector.distributions();
  }

  @Override // from FieldsAssignment
  protected void insert(Properties properties, RandomGenerator random) {
    for (Entry<String, Function<RandomGenerator, String>> entry : distributions.entrySet())
      properties.put(entry.getKey(), entry.getValue().apply(random)); // Properties requires a String as value
  }

  @Override // from FieldsAssignment
  protected boolean isGrid() {
    return distributions.isEmpty();
  }
}
