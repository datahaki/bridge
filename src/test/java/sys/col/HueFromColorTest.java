// code by jph
package sys.col;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.img.ColorDataGradients;
import ch.alpine.tensor.img.ColorFormat;
import ch.alpine.tensor.pdf.RandomVariate;
import ch.alpine.tensor.pdf.c.UniformDistribution;

class HueFromColorTest {
  @ParameterizedTest
  @EnumSource
  void test(ColorDataGradients cdg) {
    Tensor vector = RandomVariate.of(UniformDistribution.unit(), 10);
    vector.stream().map(Scalar.class::cast).map(cdg).map(ColorFormat::toColor).forEach(HueFromColor::of);
  }
}
