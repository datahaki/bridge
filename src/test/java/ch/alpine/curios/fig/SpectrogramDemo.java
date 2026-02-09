// code by jph
package ch.alpine.curios.fig;

import java.util.List;

import ch.alpine.bridge.fig.Plot;
import ch.alpine.bridge.fig.Show;
import ch.alpine.bridge.fig.ShowWindow;
import ch.alpine.bridge.fig.Showable;
import ch.alpine.bridge.fig.Spectrogram;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.alg.Subdivide;
import ch.alpine.tensor.api.ScalarUnaryOperator;
import ch.alpine.tensor.fft.ChirpFunctions;
import ch.alpine.tensor.qty.Quantity;
import ch.alpine.tensor.sca.Clips;

public enum SpectrogramDemo {
  ;
  public static Showable createLin(double lo, double hi) {
    Tensor domain = Subdivide.of(RealScalar.of(lo), RealScalar.of(hi), (int) (8000 * (hi - lo)));
    ScalarUnaryOperator linear = ChirpFunctions.linear(RealScalar.of(400), RealScalar.of(2800));
    linear = linear.andThen(Quantity.of(1, "m")::multiply);
    Tensor signal = domain.map(linear);
    return Spectrogram.of(signal, Quantity.of(8000, "s^-1"));
  }

  public static Showable createQud(double lo, double hi) {
    Tensor domain = Subdivide.of(RealScalar.of(lo), RealScalar.of(hi), (int) (8000 * (hi - lo)));
    ScalarUnaryOperator linear = ChirpFunctions.quadratic(RealScalar.of(400), RealScalar.of(4000));
    linear = linear.andThen(Quantity.of(1, "m")::multiply);
    Tensor signal = domain.map(linear);
    return Spectrogram.of(signal, Quantity.of(8000, "s^-1"));
  }

  public static Showable createExp(double lo, double hi) {
    Tensor domain = Subdivide.of(RealScalar.of(lo), RealScalar.of(hi), (int) (8000 * (hi - lo)));
    ScalarUnaryOperator linear = ChirpFunctions.exponential(RealScalar.of(400), RealScalar.of(2));
    linear = linear.andThen(Quantity.of(1, "m")::multiply);
    Tensor signal = domain.map(linear);
    return Spectrogram.of(signal, Quantity.of(8000, "s^-1"));
  }

  static void main() {
    double lo = 0.0;
    Show show1 = Showcases.SpectrogramQud.getShow();
    ScalarUnaryOperator linear = ChirpFunctions.linear(RealScalar.of(400), RealScalar.of(2800));
    linear = linear.andThen(Quantity.of(1, "m")::multiply);
    Show show = new Show();
    show.add(Plot.of(linear, Clips.interval(lo, lo + 0.1)));
    ShowWindow.asDialog(List.of(show1, show));
  }
}
