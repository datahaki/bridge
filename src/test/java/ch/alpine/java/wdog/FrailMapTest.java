// code by jph
package ch.alpine.java.wdog;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ch.alpine.java.util.AssertFail;
import ch.alpine.tensor.qty.Quantity;

public class FrailMapTest {
  @Test
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

  @Test
  public void testRegisterFail() {
    FrailMap<Integer, String> frailMap = new FrailMap<>();
    AssertFail.of(() -> frailMap.get(2));
    frailMap.registerKey(3, Quantity.of(0.01, "s"));
    assertTrue(frailMap.get(3).isEmpty());
    AssertFail.of(() -> frailMap.registerKey(3, Quantity.of(0.01, "s")));
  }
}
