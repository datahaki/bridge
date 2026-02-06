// code by jph
package showcase.img;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Function;

import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.alg.Subdivide;
import ch.alpine.tensor.api.ScalarUnaryOperator;
import ch.alpine.tensor.ext.HomeDirectory;
import ch.alpine.tensor.fft.Fourier;
import ch.alpine.tensor.fft.SpectrogramArray;
import ch.alpine.tensor.img.ColorDataGradients;
import ch.alpine.tensor.img.ImageResize;
import ch.alpine.tensor.img.Raster;
import ch.alpine.tensor.io.Export;
import ch.alpine.tensor.sca.ply.Polynomial;
import ch.alpine.tensor.sca.tri.Cos;
import ch.alpine.tensor.sca.win.WindowFunctions;

/** Example from Mathematica::Spectrogram:
 * Table[Cos[ i/4 + (i/20)^2], {i, 2000}] */
/* package */ enum SpectrogramWindowDemo {
  ;
  public static Tensor vector(Tensor vector, ScalarUnaryOperator window, Function<Scalar, ? extends Tensor> function) {
    return Raster.of(new SpectrogramArray(Fourier.FORWARD::transform, null, null, window).half_abs(vector), function);
  }

  static void main() throws IOException {
    Tensor tensor = Subdivide.of(0, 100, 2000).map(Polynomial.of(Tensors.vector(0, 5, 1))).map(Cos.FUNCTION);
    Path folder = HomeDirectory.Pictures(SpectrogramWindowDemo.class.getSimpleName());
    Files.createDirectories(folder);
    for (WindowFunctions windowFunctions : WindowFunctions.values()) {
      ScalarUnaryOperator scalarUnaryOperator = windowFunctions.get();
      Tensor image = vector(tensor, scalarUnaryOperator, ColorDataGradients.VISIBLE_SPECTRUM);
      Export.of(folder.resolve(windowFunctions.name() + ".png"), ImageResize.nearest(image, 4));
    }
  }
}
