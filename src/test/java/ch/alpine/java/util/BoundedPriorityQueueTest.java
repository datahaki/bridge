// code by jph
package ch.alpine.java.util;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.stream.IntStream;

import ch.alpine.tensor.ext.Serialization;
import ch.alpine.tensor.num.RandomPermutation;
import junit.framework.TestCase;

public class BoundedPriorityQueueTest extends TestCase {
  public void testMin() throws ClassNotFoundException, IOException {
    @SuppressWarnings("unchecked")
    Queue<Integer> queue = Serialization.copy(BoundedPriorityQueue.min(2, //
        (Comparator<Integer> & Serializable) Integer::compare));
    queue.add(9);
    queue.add(5);
    queue.add(3);
    queue.add(6);
    assertEquals(queue.poll().intValue(), 5);
    assertEquals(queue.poll().intValue(), 3);
  }

  public void testMinNonSerializable() {
    BoundedPriorityQueue.min(2, Integer::compare);
  }

  public void testMax() throws ClassNotFoundException, IOException {
    @SuppressWarnings("unchecked")
    Queue<Integer> queue = Serialization.copy(BoundedPriorityQueue.max(2, //
        (Comparator<Integer> & Serializable) Integer::compare));
    queue.add(9);
    queue.add(3);
    queue.add(5);
    queue.add(3);
    queue.add(6);
    queue.add(3);
    assertEquals(queue.poll().intValue(), 6);
    assertEquals(queue.poll().intValue(), 9);
  }

  public void testNegativeFail() {
    AssertFail.of(() -> BoundedPriorityQueue.min(0, Integer::compare));
    AssertFail.of(() -> BoundedPriorityQueue.max(0, Integer::compare));
    AssertFail.of(() -> BoundedPriorityQueue.min(-1, Integer::compare));
    AssertFail.of(() -> BoundedPriorityQueue.max(-1, Integer::compare));
  }

  public void testNaturalNull() {
    PriorityQueue<Integer> d = new PriorityQueue<>(10);
    d.add(5);
    d.add(9);
    d.add(3);
    d.add(6);
    assertEquals(d.poll().intValue(), 3);
    assertEquals(d.poll().intValue(), 5);
    assertEquals(d.poll().intValue(), 6);
  }

  public void testNaturalSpecific() {
    PriorityQueue<Integer> d = new PriorityQueue<>(10, Integer::compare);
    d.add(9);
    d.add(3);
    d.add(5);
    d.add(6);
    assertEquals(d.poll().intValue(), 3);
    assertEquals(d.poll().intValue(), 5);
    assertEquals(d.poll().intValue(), 6);
  }

  public void testPriorityQueue() {
    PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
    priorityQueue.offer(3);
    priorityQueue.offer(1);
    priorityQueue.offer(5);
    assertEquals(priorityQueue.poll().intValue(), 1);
    assertEquals(priorityQueue.poll().intValue(), 3);
    assertEquals(priorityQueue.poll().intValue(), 5);
  }

  public void testMaxOld() {
    Queue<Integer> queue = BoundedPriorityQueue.max(3, Integer::compare);
    assertTrue(queue.offer(3));
    assertEquals(queue.size(), 1);
    assertTrue(queue.offer(1));
    assertEquals(queue.size(), 2);
    assertTrue(queue.offer(3));
    assertEquals(queue.size(), 3);
    assertTrue(queue.offer(5));
    assertEquals(queue.size(), 3);
    assertFalse(queue.offer(2));
    assertEquals(queue.size(), 3);
    assertTrue(queue.offer(4));
    assertEquals(queue.size(), 3);
    assertEquals(queue.poll().intValue(), 3);
    assertEquals(queue.poll().intValue(), 4);
    assertEquals(queue.poll().intValue(), 5);
  }

  public void testPriorityQueueReverseOrder() {
    PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(Collections.reverseOrder());
    priorityQueue.offer(3);
    priorityQueue.offer(1);
    priorityQueue.offer(5);
    assertEquals(priorityQueue.poll().intValue(), 5);
    assertEquals(priorityQueue.poll().intValue(), 3);
    assertEquals(priorityQueue.poll().intValue(), 1);
  }

  public void testMinOld() {
    Queue<Integer> queue = BoundedPriorityQueue.min(3, Integer::compare);
    assertTrue(queue.offer(3));
    assertEquals(queue.size(), 1);
    assertTrue(queue.offer(1));
    assertEquals(queue.size(), 2);
    assertTrue(queue.offer(3));
    assertEquals(queue.size(), 3);
    assertFalse(queue.offer(5));
    assertEquals(queue.size(), 3);
    assertTrue(queue.offer(2));
    assertEquals(queue.size(), 3);
    assertFalse(queue.offer(4));
    assertEquals(queue.size(), 3);
    assertEquals(queue.poll().intValue(), 3);
    assertEquals(queue.poll().intValue(), 2);
    assertEquals(queue.poll().intValue(), 1);
  }

  public void testRandomMin() {
    int[] array = RandomPermutation.ofLength(13);
    Queue<Integer> queue = BoundedPriorityQueue.min(2, Integer::compare);
    IntStream.of(array).boxed().forEach(queue::add);
    assertEquals(queue.poll().intValue(), 1);
    assertEquals(queue.poll().intValue(), 0);
    assertTrue(queue.isEmpty());
  }

  public void testRandomMax() {
    int[] array = RandomPermutation.ofLength(13);
    {
      Queue<Integer> queue = BoundedPriorityQueue.max(2, Integer::compare);
      IntStream.of(array).boxed().forEach(queue::add);
      assertEquals(queue.poll().intValue(), 11);
      assertEquals(queue.poll().intValue(), 12);
      assertTrue(queue.isEmpty());
    }
    // ---
    PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
    IntStream.of(array).boxed().forEach(priorityQueue::add);
    assertEquals(priorityQueue.poll().intValue(), 0);
    assertEquals(priorityQueue.poll().intValue(), 1);
    assertEquals(priorityQueue.poll().intValue(), 2);
    assertEquals(priorityQueue.poll().intValue(), 3);
  }
}
