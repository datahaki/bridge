// code by jph
package ch.alpine.java.util;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;

public class BoundedPriorityQueue<T> extends PriorityQueue<T> {
  /** @param capacity
   * @param increasing for instance Integer::compare
   * @return */
  public static <T> Queue<T> max(int capacity, Comparator<? super T> increasing) {
    return new BoundedPriorityQueue<>(capacity, increasing);
  }

  /** @param capacity
   * @param increasing for instance Integer::compare
   * @return */
  public static <T> Queue<T> min(int capacity, Comparator<? super T> increasing) {
    @SuppressWarnings("unchecked")
    Comparator<? super T> decreasing = //
        (Comparator<? super T> & Serializable) ((i1, i2) -> increasing.compare(i2, i1));
    return new BoundedPriorityQueue<>(capacity, decreasing);
  }

  // ---
  private int capacity;

  private BoundedPriorityQueue(int capacity, Comparator<? super T> comparator) {
    super(capacity, Objects.requireNonNull(comparator));
    this.capacity = capacity;
  }

  @Override // from PriorityQueue
  public final boolean offer(final T element) {
    if (size() < capacity)
      return super.offer(element);
    if (0 < comparator().compare(element, peek())) {
      poll();
      return super.offer(element);
    }
    return false;
  }
}
