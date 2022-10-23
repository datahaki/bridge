// code by jph
package ch.alpine.bridge.usr;

import java.io.IOException;

import ch.alpine.bridge.fig.ListPlot;
import ch.alpine.bridge.fig.Show;
import ch.alpine.bridge.fig.Showable;
import ch.alpine.bridge.fig.Spectrogram;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.alg.Subdivide;
import ch.alpine.tensor.api.ScalarUnaryOperator;
import ch.alpine.tensor.img.ColorDataGradient;
import ch.alpine.tensor.img.ColorDataGradients;
import ch.alpine.tensor.num.Pi;
import ch.alpine.tensor.qty.Quantity;
import ch.alpine.tensor.sca.ply.Polynomial;
import ch.alpine.tensor.sca.tri.Cos;
import ch.alpine.tensor.sca.win.DirichletWindow;

public enum SpectrogramDemo {
  ;
  public static Showable create() {
    return create(0.32, 1.6);
  }

  public static Showable create(double lo, double hi) {
    ScalarUnaryOperator polynomial = Polynomial.of(Tensors.vector( //
        0, //
        800, //
        2800).multiply(Pi.VALUE));
    Tensor domain = Subdivide.of(RealScalar.of(lo), RealScalar.of(hi), (int) (8000 * (hi - lo)));
    Tensor signal = domain.map(polynomial).map(Cos.FUNCTION).map(s -> Quantity.of(s, "m"));
    Show show = new Show();
    show.setPlotLabel("Spectrogram");
    show.add(new ListPlot(domain.map(s -> Quantity.of(s, "s")), signal));
    show.getAxisX().setLabel("time");
    show.getAxisY().setLabel("frequency");
    ColorDataGradient colorDataGradient = ColorDataGradients.SUNSET_REVERSED;
    return Spectrogram.of(show, DirichletWindow.FUNCTION, colorDataGradient);
  }

  public static void main(String[] args) throws IOException {
    // Showable jFreeChart = create(0.0, 1.65);
    // Show.export(HomeDirectory.Pictures(Spectrogram.class.getSimpleName() + ".png"), //
    // jFreeChart, //
    // new Dimension(DemoHelper.DEMO_W, DemoHelper.DEMO_H));
  }
}
