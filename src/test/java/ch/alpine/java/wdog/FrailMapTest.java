// code by jph
package ch.alpine.java.wdog;

import ch.alpine.java.util.AssertFail;
import ch.alpine.tensor.qty.Quantity;
import junit.framework.TestCase;

public class FrailMapTest extends TestCase {
  public void testSimple() throws InterruptedException {
    FrailMap<Integer, String> frailMap = new FrailMap<>();
    assertEquals(frailMap.size(), 0);
    frailMap.registerKey(3, Quantity.of(0.01, "s"));
    assertEquals(frailMap.size(), 0);
    assertTrue(frailMap.isEmpty());
    frailMap.put(3, "asd");
    assertFalse(frailMap.isEmpty());
    assertEquals(frailMap.size(), 1);
    assertEquals(frailMap.get(3).get(), "asd");
    Thread.sleep(12);
    assertEquals(frailMap.size(), 0);
    assertFalse(frailMap.get(3).isPresent());
    assertTrue(frailMap.isEmpty());
    frailMap.clear();
  }

  public void testRegisterFail() {
    FrailMap<Integer, String> frailMap = new FrailMap<>();
    AssertFail.of(() -> frailMap.get(2));
    assertEquals(frailMap.size(), 0);
    frailMap.registerKey(3, Quantity.of(0.01, "s"));
    assertEquals(frailMap.size(), 0);
    assertTrue(frailMap.get(3).isEmpty());
  }
}
