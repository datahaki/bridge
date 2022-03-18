// code by jph
package ch.alpine.java.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.ext.Serialization;

public class BoundedSortedQueueTest {
  @Test
  public void testSimpleMin() throws ClassNotFoundException, IOException {
    BoundedSortedQueue<Double, String> boundedSortedQueue = Serialization.copy(BoundedSortedQueue.min(3));
    boundedSortedQueue.offer(0.7, "7");
    boundedSortedQueue.offer(0.0, "0");
    boundedSortedQueue.offer(0.3, "3");
    boundedSortedQueue.offer(0.1, "1");
    boundedSortedQueue.offer(0.5, "5");
    Collection<String> collection = boundedSortedQueue.values().collect(Collectors.toSet());
    assertTrue(collection.contains("0"));
    assertTrue(collection.contains("1"));
    assertTrue(collection.contains("3"));
    assertEquals(collection.size(), 3);
    {
      List<String> list = boundedSortedQueue.values().collect(Collectors.toList());
      assertEquals(list.size(), 3);
    }
  }

  @Test
  public void testScalar() {
    BoundedSortedQueue<Scalar, String> boundedSortedQueue = BoundedSortedQueue.min(3);
    boundedSortedQueue.offer(RealScalar.of(0.7), "7");
    boundedSortedQueue.offer(RealScalar.of(0.0), "0");
    boundedSortedQueue.offer(RealScalar.of(0.3), "3");
    boundedSortedQueue.offer(RealScalar.of(0.1), "1");
    boundedSortedQueue.offer(RealScalar.of(0.5), "5");
    Collection<String> collection = boundedSortedQueue.values().collect(Collectors.toSet());
    assertTrue(collection.contains("0"));
    assertTrue(collection.contains("1"));
    assertTrue(collection.contains("3"));
  }

  @Test
  public void testSimpleMax() throws ClassNotFoundException, IOException {
    BoundedSortedQueue<Double, String> boundedSortedQueue = Serialization.copy(BoundedSortedQueue.max(3));
    boundedSortedQueue.offer(0.7, "7");
    boundedSortedQueue.offer(0.0, "0");
    boundedSortedQueue.offer(0.3, "3");
    boundedSortedQueue.offer(0.1, "1");
    boundedSortedQueue.offer(0.5, "5");
    Collection<String> collection = boundedSortedQueue.values().collect(Collectors.toSet());
    assertTrue(collection.contains("7"));
    assertTrue(collection.contains("5"));
    assertTrue(collection.contains("3"));
    assertEquals(collection.size(), 3);
  }

  @Test
  public void testFail() {
    AssertFail.of(() -> BoundedSortedQueue.min(0));
    AssertFail.of(() -> BoundedSortedQueue.max(0));
    AssertFail.of(() -> BoundedSortedQueue.min(-1));
    AssertFail.of(() -> BoundedSortedQueue.max(-1));
  }
}
