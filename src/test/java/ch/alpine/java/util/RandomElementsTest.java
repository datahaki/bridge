// code by jph
package ch.alpine.java.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

class RandomElementsTest {
  @Test
  public void testEmpty() {
    List<Integer> elements = RandomElements.of(Arrays.asList(), 3);
    assertEquals(elements, Arrays.asList());
    assertThrows(Exception.class, () -> elements.add(2));
  }

  @Test
  public void testSize1() {
    List<Integer> elements = RandomElements.of(Arrays.asList(2), 3);
    assertEquals(elements, Arrays.asList(2));
    assertThrows(Exception.class, () -> elements.add(2));
  }

  @Test
  public void testSize2() {
    List<Integer> elements = RandomElements.of(Arrays.asList(2, 3), 3);
    assertEquals(elements, Arrays.asList(2, 3));
    assertThrows(Exception.class, () -> elements.add(2));
  }

  @Test
  public void testSize3() {
    List<Integer> elements = RandomElements.of(Arrays.asList(2, 3, 8), 3);
    assertEquals(elements, Arrays.asList(2, 3, 8));
    assertThrows(Exception.class, () -> elements.add(2));
  }

  @Test
  public void testSize4() {
    List<Integer> elements = RandomElements.of(Arrays.asList(2, 3, 8, 2), 3);
    assertEquals(elements.size(), 3);
    assertThrows(Exception.class, () -> elements.add(2));
  }

  @Test
  public void testSize5() {
    RandomElements.of(Arrays.asList(2, 3, 8, 2), 0);
    assertThrows(Exception.class, () -> RandomElements.of(Arrays.asList(2, 3, 8, 2), -1));
    assertThrows(Exception.class, () -> RandomElements.of(Arrays.asList(), -1));
  }
}
