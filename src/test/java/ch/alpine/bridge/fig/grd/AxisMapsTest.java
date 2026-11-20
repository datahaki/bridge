// code by jph
package ch.alpine.bridge.fig.grd;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.mat.Tolerance;

class AxisMapsTest {
  @ParameterizedTest
  @EnumSource
  void test(AxisMaps axisMaps) {
    Scalar x = RealScalar.ONE;
    Scalar y = axisMaps.model2range(x);
    Scalar z = axisMaps.range2model(y);
    Tolerance.CHOP.requireClose(x, z);
  }
}
