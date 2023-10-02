package ch.alpine.bridge.lang;

import org.junit.jupiter.api.Test;

import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.mat.Tolerance;
import ch.alpine.tensor.qty.Quantity;

class PhysicalConstantsTest {
  @Test
  void test() {
    Scalar scalar = PhysicalConstants.of("BohrRadius");
    Tolerance.CHOP.requireClose(scalar, Quantity.of(0.0529177210903, "nm"));
  }
}
