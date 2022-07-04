// code by jph
package ch.alpine.bridge.ref.util;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import ch.alpine.bridge.ref.FieldWrap;
import ch.alpine.tensor.alg.Array;

/** {@link FieldOuterProduct} creates a complete, or random set of
 * assignments of a parameter object.
 * 
 * This is useful for automatic testing of functionality when subject
 * to different assignments of a parameter object. */
public class FieldOuterProduct extends ObjectFieldIo {
  private static final Random RANDOM = new SecureRandom();

  /** @param object
   * @param consumer of given object but with fields assigned based on all possible
   * combinations suggested by the field type, and annotations */
  public static <T> void forEach(T object, Consumer<T> consumer) {
    randomize(object, consumer, Integer.MAX_VALUE);
  }

  /** Remark:
   * if given limit exceeds number of possible combinations, then
   * a complete, systematic enumeration is performed.
   * 
   * @param object
   * @param consumer of given object
   * @param limit of number of invocations */
  public static <T> void randomize(T object, Consumer<T> consumer, int limit) {
    FieldOuterProduct fieldOptions = new FieldOuterProduct();
    ObjectFields.of(object, fieldOptions);
    fieldOptions._forEach(object, consumer, limit);
  }

  private final Map<String, List<String>> map = new LinkedHashMap<>();

  private FieldOuterProduct() {
    // ---
  }

  @Override
  public void accept(String key, FieldWrap fieldWrap, Object object, Object value) {
    List<String> list = fieldWrap.options(object);
    if (1 < list.size())
      map.put(key, list);
  }

  private <T> void _forEach(T object, Consumer<T> consumer) {
    Array.forEach(list -> invoke(object, consumer, list), //
        map.values().stream().mapToInt(List::size).toArray());
  }

  private <T> void _forEach(T object, Consumer<T> consumer, int limit) {
    List<String> keys = map.keySet().stream().collect(Collectors.toList());
    int[] array = keys.stream().map(map::get).mapToInt(List::size).toArray();
    if (IntStream.of(array).reduce(Math::multiplyExact).getAsInt() < limit)
      _forEach(object, consumer);
    else
      for (int count = 0; count < limit; ++count)
        invoke(object, consumer, Arrays.stream(array).map(RANDOM::nextInt).boxed().toList());
  }

  private <T> void invoke(T object, Consumer<T> consumer, List<Integer> list) {
    Properties properties = new Properties();
    AtomicInteger atomicInteger = new AtomicInteger();
    for (String key : map.keySet())
      properties.put(key, map.get(key).get(list.get(atomicInteger.getAndIncrement())));
    consumer.accept(ObjectProperties.set(object, properties));
  }
}
