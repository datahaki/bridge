// code by jph
package ch.alpine.java.util;

import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;

import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.ext.Serialization;
import junit.framework.TestCase;

public class BoundedSortedQueueTest extends TestCase {
  public void testSimpleMin() throws ClassNotFoundException, IOException {
    BoundedSortedQueue<Double, String> boundedSortedQueue = Serialization.copy(BoundedSortedQueue.min(3));
    boundedSortedQueue.offer(0.7, "7");
    boundedSortedQueue.offer(0.0, "0");
    boundedSortedQueue.offer(0.3, "3");
    boundedSortedQueue.offer(0.1, "1");
    boundedSortedQueue.offer(0.5, "5");
    Collection<String> collection = boundedSortedQueue.stream().collect(Collectors.toSet());
    assertTrue(collection.contains("0"));
    assertTrue(collection.contains("1"));
    assertTrue(collection.contains("3"));
    assertEquals(collection.size(), 3);
  }

  public void testScalar() {
    BoundedSortedQueue<Scalar, String> boundedSortedQueue = BoundedSortedQueue.min(3);
    boundedSortedQueue.offer(RealScalar.of(0.7), "7");
    boundedSortedQueue.offer(RealScalar.of(0.0), "0");
    boundedSortedQueue.offer(RealScalar.of(0.3), "3");
    boundedSortedQueue.offer(RealScalar.of(0.1), "1");
    boundedSortedQueue.offer(RealScalar.of(0.5), "5");
    Collection<String> collection = boundedSortedQueue.stream().collect(Collectors.toSet());
    assertTrue(collection.contains("0"));
    assertTrue(collection.contains("1"));
    assertTrue(collection.contains("3"));
  }

  public void testSimpleMax() throws ClassNotFoundException, IOException {
    BoundedSortedQueue<Double, String> boundedSortedQueue = Serialization.copy(BoundedSortedQueue.max(3));
    boundedSortedQueue.offer(0.7, "7");
    boundedSortedQueue.offer(0.0, "0");
    boundedSortedQueue.offer(0.3, "3");
    boundedSortedQueue.offer(0.1, "1");
    boundedSortedQueue.offer(0.5, "5");
    Collection<String> collection = boundedSortedQueue.stream().collect(Collectors.toSet());
    assertTrue(collection.contains("7"));
    assertTrue(collection.contains("5"));
    assertTrue(collection.contains("3"));
    assertEquals(collection.size(), 3);
  }
}
