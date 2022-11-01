// code by jph
package ch.alpine.bridge.fig;

import org.junit.jupiter.api.Test;

import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.io.ResourceData;
import ch.alpine.tensor.mat.re.Det;
import ch.alpine.tensor.sca.Chop;

class MatrixPlotTest {
  @Test
  void testDet() {
    Tensor matrix = ResourceData.of("/ch/alpine/bridge/fig/hb_west0381.csv");
    Scalar det = Det.of(matrix);
    Chop._05.requireClose(det, RealScalar.of(-74654.51569269571));
  }
}
