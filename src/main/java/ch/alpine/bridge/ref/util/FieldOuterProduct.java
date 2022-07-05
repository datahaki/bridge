// code by jph
package ch.alpine.bridge.ref.util;

import java.lang.reflect.Field;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import ch.alpine.bridge.ref.FieldWrap;
import ch.alpine.bridge.ref.ann.FieldClip;
import ch.alpine.bridge.ref.ann.FieldClips;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.alg.Array;
import ch.alpine.tensor.pdf.Distribution;
import ch.alpine.tensor.pdf.RandomVariate;
import ch.alpine.tensor.pdf.c.UniformDistribution;

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
    FieldOuterProduct fieldOuterProduct = new FieldOuterProduct();
    ObjectFields.of(object, fieldOuterProduct);
    String string = ObjectProperties.join(object);
    fieldOuterProduct._forEach(object, consumer, limit);
    ObjectProperties.part(object, string);
  }

  // ---
  private final Map<String, List<String>> map = new LinkedHashMap<>();
  private final Map<String, Distribution> dis = new LinkedHashMap<>();

  private FieldOuterProduct() {
    // ---
  }

  @Override
  public void accept(String key, FieldWrap fieldWrap, Object object, Object value) {
    List<Object> list = fieldWrap.options(object);
    if (1 < list.size())
      map.put(key, list.stream().map(fieldWrap::toString).toList());
    else {
      Field field = fieldWrap.getField();
      Class<?> cls = field.getType();
      if (cls.equals(Scalar.class)) {
        FieldClip fieldClip = field.getAnnotation(FieldClip.class);
        if (Objects.nonNull(fieldClip)) {
          FieldClips fieldClips = FieldClips.wrap(fieldClip);
          if (fieldClips.isFinite())
            dis.put(key, UniformDistribution.of(fieldClips.clip()));
        }
      }
    }
  }

  private <T> void _forEach(T object, Consumer<T> consumer) {
    Array.forEach(list -> invoke(object, consumer, list), //
        map.values().stream().mapToInt(List::size).toArray());
  }

  private <T> void _forEach(T object, Consumer<T> consumer, int limit) {
    List<String> keys = map.keySet().stream().collect(Collectors.toList());
    int[] array = keys.stream().map(map::get).mapToInt(List::size).toArray();
    if (IntStream.of(array).mapToLong(i -> i).reduce(Math::multiplyExact).getAsLong() <= limit && //
        dis.size() == 0)
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
    // TODO BRIDGE impl not ideal
    for (String key : dis.keySet())
      properties.put(key, RandomVariate.of(dis.get(key), RANDOM).toString());
    consumer.accept(ObjectProperties.set(object, properties));
  }
}
