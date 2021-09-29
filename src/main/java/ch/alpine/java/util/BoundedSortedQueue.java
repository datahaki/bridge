// code by jph
package ch.alpine.java.util;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Queue;
import java.util.stream.Stream;

/** BoundedSortedQueue maintains the cost and values separately,
 * i.e. unlike {@link BoundedPriorityQueue} the value type is
 * not require to be comparable.
 * 
 * implementation is not thread safe */
public class BoundedSortedQueue<C, V> implements Serializable {
  /** @param capacity
   * @return */
  public static <C, V> BoundedSortedQueue<C, V> min(int capacity) {
    @SuppressWarnings("unchecked")
    Comparator<Entry<C, V>> comparator = (Comparator<Entry<C, V>> & Serializable) //
    (pair1, pair2) -> ((Comparable<C>) pair1.cost()).compareTo(pair2.cost());
    return new BoundedSortedQueue<>(capacity, comparator);
  }

  /** @param capacity
   * @return */
  public static <C, V> BoundedSortedQueue<C, V> max(int capacity) {
    @SuppressWarnings("unchecked")
    Comparator<Entry<C, V>> comparator = (Comparator<Entry<C, V>> & Serializable) //
    (pair1, pair2) -> ((Comparable<C>) pair2.cost()).compareTo(pair1.cost());
    return new BoundedSortedQueue<>(capacity, comparator);
  }

  // ---
  private final Queue<Entry<C, V>> queue;

  private BoundedSortedQueue(int capacity, Comparator<Entry<C, V>> comparator) {
    queue = BoundedPriorityQueue.min(capacity, comparator);
  }

  /** @param cost
   * @param value */
  public void offer(C cost, V value) {
    queue.offer(new Entry<>(cost, value));
  }

  /** @return stream of up to capacity elements */
  public Stream<V> stream() {
    return queue.stream().map(Entry::value);
  }

  private static class Entry<C, V> implements Serializable {
    private final C cost;
    private final V value;

    private Entry(C cost, V value) {
      this.cost = cost;
      this.value = value;
    }

    private C cost() {
      return cost;
    }

    private V value() {
      return value;
    }
  }
}
