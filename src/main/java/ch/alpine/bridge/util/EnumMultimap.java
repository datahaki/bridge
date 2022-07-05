// code by jph
package ch.alpine.bridge.util;

import java.util.Collection;
import java.util.EnumMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Supplier;
import java.util.stream.Stream;

public final class EnumMultimap<K extends Enum<K>, V> {
  /** CAREFUL:
   * map does not add the same value more than once to a key
   * but throws an exception!
   * This is the preferred behavior for all applications
   * that we encountered so far
   * 
   * Alternative to guava
   * MultimapBuilder.enumKeys(cls).arrayListValues().build(); */
  public static <K extends Enum<K>, V> EnumMultimap<K, V> withCopyOnWriteLinkedSet(Class<K> cls) {
    return new EnumMultimap<>(cls, CopyOnWriteLinkedSet::new);
  }

  // ---
  private final Map<K, Collection<V>> map;

  private EnumMultimap(Class<K> cls, Supplier<Collection<V>> supplier) {
    map = new EnumMap<>(cls);
    for (K key : cls.getEnumConstants())
      map.put(key, supplier.get());
  }

  /** inserts given key value pair
   * 
   * Depending on the collection type, an Exception may be thrown
   * if given value is already associated with given key.
   * 
   * @param key
   * @param value */
  public void put(K key, V value) {
    map.get(key).add(value);
  }

  /** Depending on the collection type, an Exception may be thrown
   * if given value was not previously associated with given key
   * 
   * @param key
   * @param value */
  public void remove(K key, V value) {
    map.get(key).remove(value);
  }

  /** @return stream of all values associated to all keys */
  public Stream<V> values_stream() {
    return map.values().stream().flatMap(Collection::stream);
  }

  /** @return total number of values stored in map */
  public int valuesCount() {
    return map.values().stream().mapToInt(Collection::size).sum();
  }

  /** @return whether none of the enum keys have associated values */
  public boolean isEmpty() {
    return map.values().stream().allMatch(Collection::isEmpty);
  }

  /** @return stream of all entries in map ordered according to key
   * and then values registers for the key */
  public Stream<Entry<K, V>> stream() {
    return map.entrySet().stream() //
        .flatMap(kl -> kl.getValue().stream().map(v -> ImmutableEntry.of(kl.getKey(), v)));
  }
}
