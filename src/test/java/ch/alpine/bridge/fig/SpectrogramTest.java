// code by jph
package ch.alpine.bridge.fig;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.alg.Dimensions;
import ch.alpine.tensor.alg.Subdivide;
import ch.alpine.tensor.api.ScalarUnaryOperator;
import ch.alpine.tensor.fft.SpectrogramArray;
import ch.alpine.tensor.img.ColorDataGradients;
import ch.alpine.tensor.num.Pi;
import ch.alpine.tensor.qty.Quantity;
import ch.alpine.tensor.sca.Abs;
import ch.alpine.tensor.sca.ply.Polynomial;
import ch.alpine.tensor.sca.tri.Cos;
import ch.alpine.tensor.sca.win.DirichletWindow;

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
      // Tensor points = Transpose.of(Tensors.of(domain, signal));
      Show show = new Show();
      show.setPlotLabel("Spectrogram");
      show.add(ListLinePlot.of(domain.map(s -> Quantity.of(s, "s")), signal));
      // show.getAxisX().setUnit(Unit.of("ms"));
      // show.getAxisX().setLabel("time");
      // show.getAxisY().setLabel("user defined");
      // if (count == 0)
      // show.getAxisY().setUnit(Unit.of("s^-1"));
      Spectrogram.of(signal, Quantity.of(8000, "s^-1"));
    }
  }

  @Test
  void testDefault() {
    Tensor vector = Tensor.of(IntStream.range(0, 2000) //
        .mapToDouble(i -> Math.cos(i * 0.25 + (i / 20.0) * (i / 20.0))) //
        .mapToObj(RealScalar::of));
    Showable image = Spectrogram.of(vector, Quantity.of(1, "s"), DirichletWindow.FUNCTION, ColorDataGradients.VISIBLE_SPECTRUM);
    // ImageFormat.of(image);
    // assertEquals(Dimensions.of(image), Arrays.asList(32, 93, 4));
    assertEquals(Dimensions.of(SpectrogramArray.SPECTROGRAM.half_abs(vector)), Arrays.asList(32, 93));
    Tensor tensor = SpectrogramArray.SPECTROGRAM.apply(vector).map(Abs.FUNCTION);
    assertEquals(Dimensions.of(tensor), Arrays.asList(93, 64));
  }
}
