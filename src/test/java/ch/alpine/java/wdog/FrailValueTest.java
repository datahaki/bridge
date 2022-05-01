// code by jph
package ch.alpine.java.wdog;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ch.alpine.tensor.qty.Quantity;

class FrailValueTest {
  @Test
  public void testSimple() throws InterruptedException {
    FrailValue<Integer> frailValue = new FrailValue<>(Quantity.of(0.04, "s"));
    assertTrue(frailValue.getValue().isEmpty());
    frailValue.setValue(314);
    assertTrue(frailValue.getValue().isPresent());
    Thread.sleep(60);
    assertTrue(frailValue.getValue().isEmpty());
  }
}
