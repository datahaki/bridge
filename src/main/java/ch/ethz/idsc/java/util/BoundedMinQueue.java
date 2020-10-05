// code by jph
package ch.ethz.idsc.java.util;

import java.util.Collections;
import java.util.Queue;

/** bounded min queue keeps the n smallest elements */
public class BoundedMinQueue<T> extends BoundedPriorityQueue<T> {
  private static final long serialVersionUID = 2826054911131483741L;

  /** @param capacity
   * @return bounded priority queue with given maximum capacity */
  public static <T> Queue<T> of(int capacity) {
    return new BoundedMinQueue<>(capacity);
  }

  /***************************************************/
  private BoundedMinQueue(int capacity) {
    super(capacity, Collections.reverseOrder());
  }

  @Override // from BoundedPriorityQueue
  protected boolean isFavored(Comparable<T> comparable, T peek) {
    return comparable.compareTo(peek()) < 0;
  }
}