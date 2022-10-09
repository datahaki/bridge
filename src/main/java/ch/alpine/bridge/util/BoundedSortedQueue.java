// code by jph
package ch.alpine.bridge.util;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Queue;
import java.util.stream.Stream;

/** BoundedSortedQueue maintains the cost and values separately,
 * i.e. unlike {@link BoundedPriorityQueue} the value type is
 * not require to be comparable.
 * 
 * implementation is not thread safe
 * 
 * @param <K> instance of Comparable<K> */
public class BoundedSortedQueue<K, V> implements Serializable {
  /** @param capacity positive
   * @return */
  public static <K, V> BoundedSortedQueue<K, V> min(int capacity) {
    @SuppressWarnings("unchecked")
    Comparator<Entry<K, V>> comparator = (Comparator<Entry<K, V>> & Serializable) //
    (pair1, pair2) -> ((Comparable<K>) pair2.key()).compareTo(pair1.key());
    return new BoundedSortedQueue<>(capacity, comparator);
  }

  /** @param capacity positive
   * @return */
  public static <K, V> BoundedSortedQueue<K, V> max(int capacity) {
    @SuppressWarnings("unchecked")
    Comparator<Entry<K, V>> comparator = (Comparator<Entry<K, V>> & Serializable) //
    (pair1, pair2) -> ((Comparable<K>) pair1.key()).compareTo(pair2.key());
    return new BoundedSortedQueue<>(capacity, comparator);
  }

  // ---
  private final Queue<Entry<K, V>> queue;

  private BoundedSortedQueue(int capacity, Comparator<Entry<K, V>> comparator) {
    // BoundedPriorityQueue::max is used due to its natural ordering
    queue = BoundedPriorityQueue.max(capacity, comparator);
  }

  /** @param key
   * @param value */
  public void offer(K key, V value) {
    queue.offer(new Entry<>(key, value));
  }

  /** @return stream of up to capacity values */
  public Stream<V> values() {
    return queue.stream().map(Entry::value);
  }

  private static class Entry<K, V> implements Serializable {
    private final K key;
    private final V value;

    private Entry(K key, V value) {
      this.key = key;
      this.value = value;
    }

    private K key() {
      return key;
    }

    private V value() {
      return value;
    }
  }
}
