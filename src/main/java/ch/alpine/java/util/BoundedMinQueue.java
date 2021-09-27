// code by jph
package ch.alpine.java.util;

import java.util.Collections;
import java.util.Queue;

/** bounded min queue keeps the n smallest elements */
public class BoundedMinQueue<T> extends BoundedPriorityQueue<T> {
  /** @param capacity non-negative
   * @return bounded priority queue with given maximum capacity
   * @throws Exception if given capacity is negative */
  public static <T> Queue<T> of(int capacity) {
    return new BoundedMinQueue<>(capacity);
  }

  // ---
  private BoundedMinQueue(int capacity) {
    super(capacity, Collections.reverseOrder());
  }

  @Override // from BoundedPriorityQueue
  protected boolean isFavored(Comparable<T> comparable, T peek) {
    return comparable.compareTo(peek()) < 0;
  }
}