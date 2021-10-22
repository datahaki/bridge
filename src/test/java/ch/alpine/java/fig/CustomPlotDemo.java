// code by jph
package ch.alpine.java.fig;

import java.io.IOException;

import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.alg.Subdivide;
import ch.alpine.tensor.api.ScalarUnaryOperator;
import ch.alpine.tensor.ext.HomeDirectory;
import ch.alpine.tensor.fft.Spectrogram;
import ch.alpine.tensor.img.ColorDataGradients;
import ch.alpine.tensor.io.Export;
import ch.alpine.tensor.num.Pi;
import ch.alpine.tensor.num.Polynomial;
import ch.alpine.tensor.sca.Cos;
import ch.alpine.tensor.sca.win.DirichletWindow;

public enum CustomPlotDemo {
  ;
  public static void main(String[] args) throws IOException {
    ScalarUnaryOperator polynomial = Polynomial.of(Tensors.vector( //
        0, //
        800, //
        2800).multiply(Pi.VALUE));
    // Tensor domain = Subdivide.of(RealScalar.of(0.0), RationalScalar.of(8192, 6000), 8191);
    Tensor domain = Subdivide.of(RealScalar.of(0.0), RealScalar.of(1.6), (int) (8000 * 1.6));
    Tensor chirp = domain.map(polynomial).map(Cos.FUNCTION);
    Tensor tensor = Spectrogram.of(chirp, DirichletWindow.FUNCTION, ColorDataGradients.VISIBLESPECTRUM);
    // BufferedImage bufferedImage = ImageFormat.of(tensor);
    Export.of(HomeDirectory.Pictures("spectrogram_chirp.png"), tensor);
  }
}
