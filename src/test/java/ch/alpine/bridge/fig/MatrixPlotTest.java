// code by jph
package ch.alpine.bridge.fig;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.alg.Dimensions;
import ch.alpine.tensor.io.Import;
import ch.alpine.tensor.mat.re.Det;
import ch.alpine.tensor.sca.Chop;

class MatrixPlotTest {
  @Test
  void testDet() {
    Tensor matrix = Import.of("/ch/alpine/bridge/fig/hb_west0381.csv");
    assertEquals(Dimensions.of(matrix), List.of(381, 381));
    Scalar det = Det.of(matrix);
    Chop._05.requireClose(det, RealScalar.of(-74654.51569269571));
  }
}
