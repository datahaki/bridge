// code by jph
package ch.alpine.bridge.ref.util;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import ch.alpine.bridge.ref.FieldWrap;
import ch.alpine.bridge.ref.ann.FieldClip;
import ch.alpine.bridge.ref.ann.FieldClips;
import ch.alpine.bridge.ref.ann.FieldInteger;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Scalars;
import ch.alpine.tensor.alg.Array;
import ch.alpine.tensor.alg.Range;

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
    Field field = fieldWrap.getField();
    Class<?> type = field.getType();
    // TODO BRIDGE
    // FieldSelectionArray
    // FieldSelectionCallback
    if (type.equals(Boolean.class))
      map.put(key, List.of("false", "true"));
    if (type.equals(Scalar.class)) {
      FieldInteger fieldInteger = field.getAnnotation(FieldInteger.class);
      if (Objects.nonNull(fieldInteger)) {
        FieldClip fieldClip = field.getAnnotation(FieldClip.class);
        if (Objects.nonNull(fieldClip)) {
          FieldClips fieldClips = FieldClips.wrap(fieldClip);
          if (fieldClips.isFinite()) {
            Scalar min = fieldClips.min();
            Scalar max = fieldClips.max();
            map.put(key, Range.of(Scalars.longValueExact(min), Scalars.longValueExact(max) + 1).stream() //
                .map(Object::toString).toList());
          }
        }
      }
    }
    if (type.isEnum()) {
      map.put(key, Arrays.stream(type.getEnumConstants()) //
          .map(Enum.class::cast) //
          .map(Enum::name) //
          .toList());
    }
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
