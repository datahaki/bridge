// code by jph
package ch.alpine.java.wdog;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import ch.alpine.tensor.Scalar;

/** frail map associates a given key to a {@link FrailValue}.
 * 
 * All keys have to be preallocated using {@link #registerKey(Object, Scalar)}.
 * This allows to control the max size of the map. */
public class FrailMap<K, T> {
  private final Map<K, FrailValue<T>> map = new HashMap<>();

  /** @param key
   * @param timeout */
  public void registerKey(K key, Scalar timeout) {
    map.put(key, new FrailValue<>(timeout));
  }

  /***************************************************/
  /** @param key
   * @param value */
  public void put(K key, T value) {
    FrailValue<T> frailValue = map.get(key);
    if (Objects.nonNull(frailValue))
      frailValue.setValue(value);
  }

  /** @param key
   * @return value associated to key if timeout has not elapsed
   * @throws Exception if key was not previously registered */
  public Optional<T> get(K key) {
    return map.get(key).getValue();
  }

  /***************************************************/
  /** Careful: determining of the size is not thread-safe
   * 
   * @return number of keys that presently have not elapsed */
  public int size() {
    return (int) map.values().stream() //
        .map(FrailValue::getValue) //
        .filter(Optional::isPresent) //
        .count();
  }

  /** Careful: determining of the size is not thread-safe
   * 
   * @return whether map contains entries */
  public boolean isEmpty() {
    return map.values().stream() //
        .map(FrailValue::getValue) //
        .allMatch(Optional::isEmpty);
  }

  /** Careful: clearing the map is not thread-safe
   * 
   * removes all entries from map */
  public void clear() {
    map.clear();
  }
}
