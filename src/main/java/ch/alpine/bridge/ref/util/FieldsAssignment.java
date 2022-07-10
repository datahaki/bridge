// code by jph
package ch.alpine.bridge.ref.util;

import java.io.Serializable;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Scalars;
import ch.alpine.tensor.alg.Array;
import ch.alpine.tensor.ext.Integers;

/** The modifications occur on the given object.
 * The given object does not need to be instance of {@link Serializable}. */
public abstract class FieldsAssignment {
  protected static final Random RANDOM = new SecureRandom();
  // ---
  private final Object object;
  private final Runnable runnable;
  private final String string;
  protected final FieldOptionsCollector fieldOptionsCollector;
  private final Map<String, List<String>> map;
  private final List<String> keys;
  protected final int[] array;
  protected final Scalar total;

  /** @param object
   * @param runnable of given object but with fields assigned based on all possible
   * combinations suggested by the field type, and annotations */
  protected FieldsAssignment(Object object, Runnable runnable) {
    this.object = object;
    this.runnable = runnable;
    string = ObjectProperties.join(object);
    fieldOptionsCollector = new FieldOptionsCollector();
    ObjectFields.of(object, fieldOptionsCollector);
    this.map = fieldOptionsCollector.map();
    keys = map.keySet().stream().collect(Collectors.toList());
    array = keys.stream().map(map::get).mapToInt(List::size).toArray();
    total = IntStream.of(array) //
        .mapToObj(RealScalar::of) //
        .reduce(Scalar::multiply) //
        .orElse(RealScalar.ONE);
  }

  /** restore original content of given object */
  public final void restore() {
    ObjectProperties.part(object, string);
  }

  /** Careful: the number of combinations may be large */
  public final void forEach() {
    Array.forEach(list -> build(list), array);
  }

  private void build(List<Integer> list) {
    Properties properties = new Properties();
    AtomicInteger atomicInteger = new AtomicInteger();
    for (String key : keys)
      properties.put(key, map.get(key).get(list.get(atomicInteger.getAndIncrement())));
    ObjectProperties.set(object, properties);
    runnable.run();
  }

  /** @param random
   * @param limit of number of invocations */
  public final void randomize(Random random, int limit) {
    Integers.requirePositiveOrZero(limit);
    if (Scalars.lessEquals(total, RealScalar.of(limit)) && isGrid())
      forEach();
    else
      for (int count = 0; count < limit; ++count)
        build(Integers.asList(Arrays.stream(array).map(random::nextInt).toArray()), random);
  }

  public final void randomize(int limit) {
    randomize(RANDOM, limit);
  }

  private void build(List<Integer> list, Random random) {
    Properties properties = new Properties();
    AtomicInteger atomicInteger = new AtomicInteger();
    for (String key : keys)
      properties.put(key, map.get(key).get(list.get(atomicInteger.getAndIncrement())));
    insert(properties, random);
    ObjectProperties.set(object, properties);
    runnable.run();
  }

  /** @param properties
   * @param random */
  protected abstract void insert(Properties properties, Random random);

  /** @return whether enumeration is random free */
  protected abstract boolean isGrid();
}
