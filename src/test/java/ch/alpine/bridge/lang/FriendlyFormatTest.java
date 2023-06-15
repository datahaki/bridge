// code by jph
package ch.alpine.bridge.lang;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.qty.Quantity;

class FriendlyFormatTest {
  @Test
  void test() {
    Scalar scalar = FriendlyFormat.of(100000000, "B");
    assertEquals(scalar, Quantity.of(100, "MB"));
  }
}
