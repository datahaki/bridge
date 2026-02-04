// code by jph
package sys.dat;

import java.util.Comparator;
import java.util.Map;

public enum MapComparator {
  ;
  public static <T> Comparator<T> integer(final Map<T, Integer> map) {
    return Comparator.comparingInt(map::get);
  }

  public static <T> Comparator<T> integerReverse(final Map<T, Integer> map) {
    return (key1, key2) -> {
      if (!map.containsKey(key1))
        throw new RuntimeException("!contained " + key1);
      if (!map.containsKey(key2))
        throw new RuntimeException("!contained " + key2);
      return Integer.compare(map.get(key2), map.get(key1));
    };
  }

  public static <T> Comparator<T> doubleReverse(final Map<T, Double> map) {
    return (key1, key2) -> Double.compare(map.get(key2), map.get(key1));
  }
}
