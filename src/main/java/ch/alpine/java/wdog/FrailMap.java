// code by jph
package ch.alpine.java.wdog;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import ch.alpine.tensor.Scalar;

public class FrailMap<K, T> {
  private final Map<K, FrailValue<T>> map = new HashMap<>();

  public void registerKey(K key, Scalar timeout) {
    map.put(key, new FrailValue<>(timeout));
  }

  public void put(K key, T value) {
    FrailValue<T> frailValue = map.get(key);
    if (Objects.nonNull(frailValue))
      frailValue.setValue(value);
  }

  public Optional<T> get(K key) {
    FrailValue<T> frailValue = map.get(key);
    return Objects.nonNull(frailValue) //
        ? frailValue.getValue()
        : Optional.empty();
  }
}
