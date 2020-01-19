// code by jph
package ch.ethz.idsc.java.util;

import java.util.Map;
import java.util.stream.IntStream;

import junit.framework.TestCase;

public class LruCacheTest extends TestCase {
  public void testLru1() {
    Map<Integer, String> map = new LruCache<>(2);
    map.put(3, "1");
    map.put(4, "2");
    {
      int v = map.keySet().iterator().next();
      assertEquals(v, 3);
    }
    map.get(3);
    {
      int v = map.keySet().iterator().next();
      assertEquals(v, 4);
    }
    String m1 = map.toString();
    assertTrue(map.containsKey(4)); // does not change order
    assertEquals(m1, map.toString());
    assertTrue(map.containsKey(3)); // does not change order
    assertEquals(m1, map.toString());
    map.get(3);
    assertEquals(m1, map.toString());
    map.put(4, "0");
    {
      int v = map.keySet().iterator().next();
      assertEquals(v, 3);
    }
  }

  public void testMax() {
    Map<Integer, String> map = new LruCache<>(3);
    IntStream.range(0, 100).forEach(i -> map.put(i, "" + i));
    assertEquals(map.size(), 3);
  }
}
