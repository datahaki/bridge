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
import java.util.stream.Stream;

import ch.alpine.bridge.ref.FieldWrap;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Scalars;
import ch.alpine.tensor.ext.Integers;

/** OuterFieldsAssignment creates a complete, or randomized set of
 * assignments of a parameter object. The assignments are based on
 * {@link FieldWrap#options(Object)}.
 * 
 * This is useful for automatic testing of functionality when subject
 * to different assignments of a parameter object.
 * 
 * Remark:
 * If given limit in {@link #randomize(int)} exceeds number of possible
 * combinations, then a complete, systematic enumeration is performed.
 * 
 * The modifications occur on the given object.
 * The given object does not need to be instance of {@link Serializable}. */
public class FieldsAssignment {
  protected static final Random RANDOM = new SecureRandom();

  /** @param object */
  public static FieldsAssignment of(Object object) {
    return new FieldsAssignment(object);
  }

  // ---
  private final Object object;
  private final String string;
  protected final FieldOptionsCollector fieldOptionsCollector;
  private final Map<String, List<String>> map;
  private final List<String> keys;
  protected final int[] array;
  protected final Scalar total;

  /** @param object */
  protected FieldsAssignment(Object object) {
    this.object = object;
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

  /** @return stream of given object, i.e. always the same instance, but with fields
   * assigned to all combinations determined by options per field */
  public final Stream<Object> stream() {
    return TodoRemove.stream(array).map(this::build);
  }

  private Object build(List<Integer> list) {
    Properties properties = new Properties();
    AtomicInteger atomicInteger = new AtomicInteger();
    for (String key : keys)
      properties.put(key, map.get(key).get(list.get(atomicInteger.getAndIncrement())));
    return ObjectProperties.set(object, properties);
  }

  /** @param random
   * @param limit of number of invocations
   * @return stream of given object, i.e. always the same instance */
  public final Stream<Object> randomize(Random random, int limit) {
    Integers.requirePositiveOrZero(limit);
    return Scalars.lessEquals(total, RealScalar.of(limit)) && isGrid() //
        ? stream()
        : IntStream.range(0, limit) //
            .mapToObj(i -> Integers.asList(Arrays.stream(array).map(random::nextInt).toArray())) //
            .map(list -> build(list, random));
  }

  /** @param limit
   * @return stream of given object, i.e. always the same instance */
  public final Stream<Object> randomize(int limit) {
    return randomize(RANDOM, limit);
  }

  private Object build(List<Integer> list, Random random) {
    Properties properties = new Properties();
    AtomicInteger atomicInteger = new AtomicInteger();
    for (String key : keys)
      properties.put(key, map.get(key).get(list.get(atomicInteger.getAndIncrement())));
    insert(properties, random);
    return ObjectProperties.set(object, properties);
  }

  /** Hint:
   * {@link RandomFieldsAssignment} overrides this method
   * for instance to insert random values from continuous distributions
   * to given properties
   * 
   * @param properties
   * @param random */
  protected void insert(Properties properties, Random random) {
    // ---
  }

  /** Hint:
   * {@link RandomFieldsAssignment} overrides this method
   * 
   * @return whether enumeration is random free */
  protected boolean isGrid() {
    return true;
  }
}
