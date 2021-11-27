// code by jph
package ch.alpine.java.ref;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

import junit.framework.TestCase;

public class ClassHierarchyTest extends TestCase {
  public void testOrder() {
    Deque<Class<?>> deque = ClassHierarchy.of(ArrayList.class);
    List<String> list = deque.stream().map(Class::getSimpleName).collect(Collectors.toList());
    assertEquals(list.toString(), "[Object, AbstractCollection, AbstractList, ArrayList]");
  }

  public void testNull() {
    Deque<Class<?>> deque = ClassHierarchy.of(null);
    assertTrue(deque.isEmpty());
  }
}
