// code by jph
package ch.alpine.bridge.lang;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

import ch.alpine.tensor.ext.ArgMin;
import ch.alpine.tensor.ext.Cache;
import ch.alpine.tensor.ext.EditDistance;

public enum EnumValue {
  ;
  private static class Inner<T extends Enum<T>> implements Function<String, T> {
    private static final int FACTOR = 4;
    // ---
    private final T[] array;
    private final String[] names;
    private final Cache<String, T> cache;

    private Inner(T[] array) {
      this.array = array;
      names = Stream.of(array).map(Enum::name).map(String::toUpperCase).toArray(String[]::new);
      cache = Cache.of(this, array.length * FACTOR);
    }

    public T match(String key) {
      return cache.apply(key.toUpperCase());
    }

    @Override
    public T apply(String key) {
      return array[ArgMin.of(Stream.of(names) //
          .map(EditDistance.function(key)) //
          .toList())];
    }
  }

  private static final Map<Class<?>, Inner<?>> MAP = new HashMap<>();

  /** @param cls
   * @param string
   * @return */
  @SuppressWarnings("unchecked")
  public static <T extends Enum<T>> T match(Class<T> cls, String string) {
    Inner<?> inner = MAP.get(cls);
    if (Objects.isNull(inner))
      synchronized (MAP) {
        MAP.put(cls, inner = new Inner<>(cls.getEnumConstants()));
      }
    return (T) inner.match(string);
  }
}
