// code by jph
package ch.alpine.bridge.util;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;

public class BoundedPriorityQueue<T> extends PriorityQueue<T> {
  /** @param capacity strictly positive
   * @param increasing for instance Integer::compare
   * @return a priority queue that maintains only the smallest elements bounded in size
   * by given capacity. when polling elements, the queue removes greatest elements first. */
  public static <T> Queue<T> min(int capacity, Comparator<? super T> increasing) {
    @SuppressWarnings("unchecked")
    Comparator<? super T> decreasing = //
        (Comparator<? super T> & Serializable) ((i1, i2) -> increasing.compare(i2, i1));
    return new BoundedPriorityQueue<>(capacity, decreasing);
  }

  /** @param capacity strictly positive
   * @param increasing for instance Integer::compare
   * @return a priority queue that maintains only the greatest elements bounded in size
   * by given capacity. when polling elements, the queue removes smallest elements first. */
  public static <T> Queue<T> max(int capacity, Comparator<? super T> increasing) {
    return new BoundedPriorityQueue<>(capacity, increasing);
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
