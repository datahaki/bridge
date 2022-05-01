// code by jph
package ch.alpine.java.ref;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

class ClassHierarchyTest {
  @Test
  public void testOrder() {
    Deque<Class<?>> deque = ClassHierarchy.of(ArrayList.class);
    List<String> list = deque.stream().map(Class::getSimpleName).collect(Collectors.toList());
    assertEquals(list.toString(), "[Object, AbstractCollection, AbstractList, ArrayList]");
  }

  @Test
  public void testNull() {
    Deque<Class<?>> deque = ClassHierarchy.of(null);
    assertTrue(deque.isEmpty());
  }
}
