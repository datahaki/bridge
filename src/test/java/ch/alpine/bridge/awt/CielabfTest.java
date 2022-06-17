// code by jph
package ch.alpine.bridge.awt;

import org.junit.jupiter.api.Test;

import ch.alpine.tensor.RationalScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.alg.Subdivide;
import ch.alpine.tensor.mat.Tolerance;
import ch.alpine.tensor.sca.Clips;

class CielabfTest {
  @Test
  void test() {
    Scalar x = RationalScalar.HALF;
    Scalar inverse = Cielabf.inverse(Cielabf.forward(x));
    Tolerance.CHOP.requireClose(x, inverse);
  }

  @Test
  void testSubdiv() {
    for (Tensor _x : Subdivide.increasing(Clips.unit(), 12)) {
      Scalar x = (Scalar) _x;
      Scalar inverse = Cielabf.inverse(Cielabf.forward(x));
      Tolerance.CHOP.requireClose(x, inverse);
    }
  }
}
