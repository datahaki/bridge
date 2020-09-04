// code by jph
package ch.ethz.idsc.java.util;

import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

public class RandomElementsTest extends TestCase {
  public void testEmpty() {
    List<Integer> elements = RandomElements.of(Arrays.asList(), 3);
    assertEquals(elements, Arrays.asList());
    try {
      elements.add(2);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testSize1() {
    List<Integer> elements = RandomElements.of(Arrays.asList(2), 3);
    assertEquals(elements, Arrays.asList(2));
    try {
      elements.add(2);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testSize2() {
    List<Integer> elements = RandomElements.of(Arrays.asList(2, 3), 3);
    assertEquals(elements, Arrays.asList(2, 3));
    try {
      elements.add(2);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testSize3() {
    List<Integer> elements = RandomElements.of(Arrays.asList(2, 3, 8), 3);
    assertEquals(elements, Arrays.asList(2, 3, 8));
    try {
      elements.add(2);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testSize4() {
    List<Integer> elements = RandomElements.of(Arrays.asList(2, 3, 8, 2), 3);
    assertEquals(elements.size(), 3);
    try {
      elements.add(2);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testSize5() {
    RandomElements.of(Arrays.asList(2, 3, 8, 2), 0);
    try {
      RandomElements.of(Arrays.asList(2, 3, 8, 2), -1);
      fail();
    } catch (Exception exception) {
      // ---
    }
    try {
      RandomElements.of(Arrays.asList(), -1);
      fail();
    } catch (Exception exception) {
      // ---
    }
  }
}
