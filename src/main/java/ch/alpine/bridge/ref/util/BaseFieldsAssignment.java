// code by jph
package ch.alpine.bridge.ref.util;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.ext.Integers;

/* package */ abstract class BaseFieldsAssignment<T> {
  protected static final Random RANDOM = new SecureRandom();
  // ---
  private final T object;
  private final Consumer<T> consumer;
  private final String string;
  protected final FieldOptionsCollector fieldOptionsCollector;
  private final Map<String, List<String>> map;
  private final List<String> keys;
  protected final int[] array;
  protected final Scalar total;

  /** @param object
   * @param consumer of given object but with fields assigned based on all possible
   * combinations suggested by the field type, and annotations */
  protected BaseFieldsAssignment(T object, Consumer<T> consumer) {
    this.object = object;
    this.consumer = consumer;
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

  /** @param random
   * @param limit of number of invocations */
  public void randomize(Random random, int limit) {
    Integers.requirePositiveOrZero(limit);
    for (int count = 0; count < limit; ++count) {
      List<Integer> list = Integers.asList(Arrays.stream(array).map(random::nextInt).toArray());
      build(list, random);
    }
  }

  public final void randomize(int limit) {
    randomize(RANDOM, limit);
  }

  protected abstract void insert(Properties properties, Random random);

  protected final void build(List<Integer> list, Random random) {
    Properties properties = new Properties();
    AtomicInteger atomicInteger = new AtomicInteger();
    for (String key : keys)
      properties.put(key, map.get(key).get(list.get(atomicInteger.getAndIncrement())));
    insert(properties, random);
    consumer.accept(ObjectProperties.set(object, properties));
  }

  /** restore original content of given object */
  public final void restore() {
    ObjectProperties.part(object, string);
  }
}
