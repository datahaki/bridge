// code by jph
package ch.alpine.bridge.fig;

import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.alg.Subdivide;
import ch.alpine.tensor.api.ScalarUnaryOperator;
import ch.alpine.tensor.num.Pi;
import ch.alpine.tensor.qty.Quantity;
import ch.alpine.tensor.sca.ply.Polynomial;
import ch.alpine.tensor.sca.tri.Cos;

public enum SpectrogramDemo {
  ;
  public static Showable create(double lo, double hi) {
    ScalarUnaryOperator polynomial = Polynomial.of(Tensors.vector( //
        0, //
        800, //
        2800).multiply(Pi.VALUE));
    Tensor domain = Subdivide.of(RealScalar.of(lo), RealScalar.of(hi), (int) (8000 * (hi - lo)));
    Tensor signal = domain.map(polynomial).map(Cos.FUNCTION).map(s -> Quantity.of(s, "m"));
    return Spectrogram.of(signal, Quantity.of(8000, "s^-1"));
  }

  public static void main(String[] args) {
    ShowDialog.of(ShowDemos.SPECTROGRAM0.create());
  }
}
