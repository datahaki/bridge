// code by jph
package ch.alpine.java.wdog;

import ch.alpine.tensor.qty.Quantity;
import junit.framework.TestCase;

public class FrailValueTest extends TestCase {
  public void testSimple() throws InterruptedException {
    FrailValue<Integer> frailValue = new FrailValue<>(Quantity.of(0.04, "s"));
    assertTrue(frailValue.getValue().isEmpty());
    frailValue.setValue(314);
    assertTrue(frailValue.getValue().isPresent());
    Thread.sleep(60);
    assertTrue(frailValue.getValue().isEmpty());
  }
}
