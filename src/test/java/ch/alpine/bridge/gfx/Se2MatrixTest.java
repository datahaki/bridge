// code by jph
package ch.alpine.bridge.gfx;

import org.junit.jupiter.api.Test;

import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.mat.Tolerance;
import ch.alpine.tensor.pdf.RandomVariate;
import ch.alpine.tensor.pdf.c.UniformDistribution;

class Se2MatrixTest {
  @Test
  void test() {
    Tensor xya = RandomVariate.of(UniformDistribution.unit(), 2).append(RealScalar.ZERO);
    Tolerance.CHOP.requireClose(Se2Matrix.of(xya), Se2Matrix.translation(xya));
  }
}
