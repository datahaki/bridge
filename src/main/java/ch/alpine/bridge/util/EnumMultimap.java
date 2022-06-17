// code by jph
package ch.alpine.bridge.util;

import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/** CAREFUL:
 * map does not add the same value more than once
 * but throws an exception!
 * This is the preferred behavior for all applications
 * that we encountered so far
 * 
 * Java 11 alternative to guice
 * MultimapBuilder.enumKeys(clazz).arrayListValues().build(); */
public final class EnumMultimap<K extends Enum<K>, V> {
  private final Map<K, List<V>> map;

  public EnumMultimap(Class<K> cls) {
    map = new EnumMap<>(cls);
    for (K providerRank : cls.getEnumConstants())
      map.put(providerRank, new CopyOnWriteArrayList<>());
  }

  public void put(K key, V value) {
    List<V> list = map.get(key);
    if (list.contains(value))
      throw new IllegalArgumentException(key + ": already in list " + value);
    list.add(value);
  }

  public void remove(K key, V value) {
    List<V> list = map.get(key);
    if (!list.remove(value))
      throw new IllegalArgumentException(key + ": not in list " + value);
  }

  /** @return */
  // TODO BRDIGE API no good
  public Collection<List<V>> values() {
    return map.values().stream().map(Collections::unmodifiableList).collect(Collectors.toList());
  }

  /** @return total number of values stored in map */
  public int size() {
    return map.values().stream().mapToInt(List::size).sum();
  }

  /** @return */
  public boolean isEmpty() {
    return map.values().stream().allMatch(List::isEmpty);
  }

  public Stream<Entry<K, V>> stream() {
    return map.entrySet().stream() //
        .flatMap(kl -> kl.getValue().stream().map(v -> ImmutableEntry.of(kl.getKey(), v)));
  }
}
