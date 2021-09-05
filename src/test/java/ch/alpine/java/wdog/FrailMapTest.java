// code by jph
package ch.alpine.java.wdog;

import ch.alpine.java.util.AssertFail;
import ch.alpine.tensor.qty.Quantity;
import junit.framework.TestCase;

public class FrailMapTest extends TestCase {
  public void testSimple() throws InterruptedException {
    FrailMap<Integer, String> frailMap = new FrailMap<>();
    frailMap.put(3, "asd");
    frailMap.registerKey(3, Quantity.of(0.01, "s"));
    assertFalse(frailMap.allPresent());
    frailMap.put(3, "asd");
    assertTrue(frailMap.allPresent());
    assertEquals(frailMap.keySet().size(), 1);
    assertTrue(frailMap.keySet().contains(3));
    assertEquals(frailMap.get(3).get(), "asd");
    Thread.sleep(12);
    assertFalse(frailMap.get(3).isPresent());
  }

  public void testRegisterFail() {
    FrailMap<Integer, String> frailMap = new FrailMap<>();
    AssertFail.of(() -> frailMap.get(2));
    frailMap.registerKey(3, Quantity.of(0.01, "s"));
    assertTrue(frailMap.get(3).isEmpty());
    AssertFail.of(() -> frailMap.registerKey(3, Quantity.of(0.01, "s")));
  }
}
