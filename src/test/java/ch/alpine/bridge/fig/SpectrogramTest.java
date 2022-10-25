// code by jph
package ch.alpine.bridge.fig;

import org.junit.jupiter.api.Test;

import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.alg.Subdivide;
import ch.alpine.tensor.alg.Transpose;
import ch.alpine.tensor.api.ScalarUnaryOperator;
import ch.alpine.tensor.num.Pi;
import ch.alpine.tensor.qty.Quantity;
import ch.alpine.tensor.sca.ply.Polynomial;
import ch.alpine.tensor.sca.tri.Cos;

class SpectrogramTest {
  @Test
  void testMore() {
    for (int count = 0; count < 2; ++count) {
      ScalarUnaryOperator polynomial = Polynomial.of(Tensors.vector( //
          0, //
          800, //
          2800).multiply(Pi.VALUE));
      double lo = 0.32;
      double hi = 1.6;
      Tensor domain = Subdivide.of(RealScalar.of(lo), RealScalar.of(hi), (int) (8000 * (hi - lo)));
      Tensor signal = domain.map(polynomial).map(Cos.FUNCTION);
      Tensor points = Transpose.of(Tensors.of(domain, signal));
      Show show = new Show();
      show.setPlotLabel("Spectrogram");
      show.add(ListLinePlot.of(domain.map(s -> Quantity.of(s, "s")), signal));
      // show.getAxisX().setUnit(Unit.of("ms"));
      // show.getAxisX().setLabel("time");
      // show.getAxisY().setLabel("user defined");
      // if (count == 0)
      // show.getAxisY().setUnit(Unit.of("s^-1"));
      Spectrogram.of(points);
    }
  }
}
