// code by jph
package ch.alpine.bridge.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.alg.OrderedQ;

class CopyOnWriteLinkedSetTest {
  @Test
  void testString() {
    Set<String> set = new CopyOnWriteLinkedSet<>();
    assertThrows(NullPointerException.class, () -> set.add(null));
    assertTrue(set.add("asd"));
    assertThrows(Exception.class, () -> set.add("asd"));
    assertThrows(Exception.class, () -> set.remove("0"));
    assertTrue(set.remove("asd"));
    assertThrows(Exception.class, () -> set.remove("asd"));
    assertThrows(NullPointerException.class, () -> set.remove(null));
  }

  @Test
  void testInteger() {
    Set<Integer> set = new CopyOnWriteLinkedSet<>();
    assertThrows(NullPointerException.class, () -> set.add(null));
    assertEquals(set.size(), 0);
    assertTrue(set.isEmpty());
    assertTrue(set.add(123));
    assertEquals(set.size(), 1);
    assertFalse(set.isEmpty());
    assertThrows(Exception.class, () -> set.add(123));
    assertThrows(Exception.class, () -> set.remove(2220));
    assertTrue(set.remove(123));
    assertThrows(Exception.class, () -> set.remove(123));
    assertThrows(NullPointerException.class, () -> set.remove(null));
  }

  @Test
  void testIntegerOrder() {
    Set<Integer> set = new CopyOnWriteLinkedSet<>();
    for (int index = 0; index < 100; ++index)
      set.add(index);
    List<Integer> list = set.stream().collect(Collectors.toList());
    assertEquals(list.size(), 100);
    OrderedQ.require(Tensors.vector(list));
  }

  @Test
  void testIterator() {
    Set<Integer> set = new CopyOnWriteLinkedSet<>();
    for (int index = 0; index < 10; ++index)
      set.add(index);
    int i = 0;
    for (Iterator<Integer> iterator = set.iterator(); iterator.hasNext();) {
      Integer integer = iterator.next();
      assertEquals(integer, i);
      ++i;
      // iterator.remove(); // throws an exception
    }
    assertTrue(set.contains(3));
    assertFalse(set.contains(11));
    assertFalse(set.isEmpty());
    set.clear();
    assertTrue(set.isEmpty());
    assertFalse(set.contains(3));
  }

  @Test
  void testIntegerAddAll() {
    Set<Integer> set = new CopyOnWriteLinkedSet<>();
    assertTrue(set.addAll(List.of(2, 3)));
    assertTrue(set.containsAll(List.of(2, 3)));
    assertTrue(set.containsAll(List.of(2, 3, 3)));
    assertFalse(set.containsAll(List.of(2, 3, 4)));
    assertThrows(Exception.class, () -> set.addAll(List.of(0, 1, 2)));
  }
}
