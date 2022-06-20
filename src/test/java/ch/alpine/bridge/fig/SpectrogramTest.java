// code by jph
package ch.alpine.bridge.fig;

import org.junit.jupiter.api.Test;

import ch.alpine.bridge.usr.SpectrogramDemo;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.alg.Subdivide;
import ch.alpine.tensor.api.ScalarUnaryOperator;
import ch.alpine.tensor.num.Pi;
import ch.alpine.tensor.num.Polynomial;
import ch.alpine.tensor.qty.Quantity;
import ch.alpine.tensor.qty.Unit;
import ch.alpine.tensor.sca.tri.Cos;

class SpectrogramTest {
  @Test
  void testSimple() {
    CascadeHelper.draw(SpectrogramDemo.create(0.4, 1.7));
  }

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
      VisualSet visualSet = new VisualSet();
      visualSet.setPlotLabel("Spectrogram");
      visualSet.add(domain.map(s -> Quantity.of(s, "s")), signal);
      visualSet.getAxisX().setUnit(Unit.of("ms"));
      visualSet.getAxisX().setLabel("time");
      visualSet.getAxisY().setLabel("user defined");
      if (count == 0)
        visualSet.getAxisY().setUnit(Unit.of("s^-1"));
      CascadeHelper.draw(Spectrogram.of(visualSet));
    }
  }
}
