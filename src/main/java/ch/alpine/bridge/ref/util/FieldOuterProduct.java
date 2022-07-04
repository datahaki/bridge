// code by jph
package ch.alpine.bridge.ref.util;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import ch.alpine.bridge.ref.FieldWrap;
import ch.alpine.tensor.alg.Array;

public class FieldOuterProduct extends ObjectFieldIo {
  /** @param object
   * @param consumer of given object but with fields assigned based on all possible
   * combinations suggested by the field type, and annotations */
  public static <T> void forEach(T object, Consumer<T> consumer) {
    FieldOuterProduct fieldOptions = new FieldOuterProduct();
    ObjectFields.of(object, fieldOptions);
    fieldOptions._forEach(object, consumer);
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
    List<String> keys = map.keySet().stream().collect(Collectors.toList());
    Array.forEach(list -> {
      Properties properties = new Properties();
      AtomicInteger atomicInteger = new AtomicInteger();
      for (String key : keys)
        properties.put(key, map.get(key).get(list.get(atomicInteger.getAndIncrement())));
      consumer.accept(ObjectProperties.set(object, properties));
    }, keys.stream().map(map::get).mapToInt(List::size).toArray());
  }
}
