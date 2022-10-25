// code by jph
package ch.alpine.bridge.usr;

import java.awt.Dimension;
import java.io.IOException;

import ch.alpine.bridge.fig.Show;
import ch.alpine.bridge.fig.Showable;
import ch.alpine.bridge.fig.Spectrogram;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.alg.Subdivide;
import ch.alpine.tensor.alg.Transpose;
import ch.alpine.tensor.api.ScalarUnaryOperator;
import ch.alpine.tensor.ext.HomeDirectory;
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
    Tensor points = Transpose.of(Tensors.of(domain.map(s -> Quantity.of(s, "s")), signal));
    return Spectrogram.of(points);
  }

  public static void main(String[] args) throws IOException {
    Show show = ShowDemos.SPECTROGRAM0.create();
    show.export(HomeDirectory.Pictures(Spectrogram.class.getSimpleName() + ".png"), //
        new Dimension(512, 288));
  }
}
