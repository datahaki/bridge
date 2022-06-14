// code by jph
package ch.alpine.bridge.ref;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import org.junit.jupiter.api.Test;

import ch.alpine.tensor.qty.Quantity;

class ParamContainerTest {
  @Test
  void testSimple() {
    ParamContainer paramContainer = ParamContainer.INSTANCE;
    assertInstanceOf(Quantity.class, paramContainer.maxTor);
    assertEquals(paramContainer.shape.length(), 4);
  }
}
