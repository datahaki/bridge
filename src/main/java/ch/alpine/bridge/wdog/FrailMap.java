// code by jph
package ch.alpine.bridge.wdog;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import ch.alpine.tensor.Scalar;

/** frail map associates a given key to a {@link FrailValue}.
 * 
 * All keys have to be preallocated using {@link #registerKey(Object, Scalar)}.
 * This allows to control the max size of the map. */
public class FrailMap<K, T> {
  private final Map<K, FrailValue<T>> map = new HashMap<>();

  /** @param key
   * @param timeout with unit compatible with "s", "ms", "us", "ns", ...
   * @throws Exception if key was already registered */
  public void registerKey(K key, Scalar timeout) {
    if (map.containsKey(key))
      throw new IllegalArgumentException();
    map.put(key, new FrailValue<>(timeout));
  }

  /** Careful: if the key is unknown to the map, i.e. the key
   * was not registered via {@link #registerKey(Object, Scalar)}
   * the key-value pair will not be stored.
   * 
   * @param key
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

  /** @return unmodifiable set of registered keys */
  public Set<K> keySet() {
    return Collections.unmodifiableSet(map.keySet());
  }

  /** @return whether at time of function invocation all keys have recent
   * associated values */
  public boolean allPresent() {
    return map.values().stream() //
        .map(FrailValue::getValue) //
        .allMatch(Optional::isPresent);
  }
}
