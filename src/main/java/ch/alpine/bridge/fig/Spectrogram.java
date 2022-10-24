// code by jph
package ch.alpine.bridge.fig;

import java.awt.image.BufferedImage;
import java.util.function.Function;

import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.alg.Last;
import ch.alpine.tensor.alg.OrderedQ;
import ch.alpine.tensor.api.ScalarUnaryOperator;
import ch.alpine.tensor.fft.SpectrogramArray;
import ch.alpine.tensor.img.ColorDataGradients;
import ch.alpine.tensor.img.Raster;
import ch.alpine.tensor.io.ImageFormat;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;
import ch.alpine.tensor.sca.Clip;
import ch.alpine.tensor.sca.Clips;
import ch.alpine.tensor.sca.win.DirichletWindow;
import ch.alpine.tensor.sca.win.HannWindow;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Spectrogram.html">Spectrogram</a> */
public enum Spectrogram {
  ;
  /** Remark: the unit of the signal is in the color not the axis
   * 
   * @param visualSet with single {@link VisualRow} containing domain and signal
   * @param window for instance {@link HannWindow#FUNCTION}
   * @param function for instance {@link ColorDataGradients#VISIBLE_SPECTRUM}
   * @return */
  public static Showable of(Tensor points, ScalarUnaryOperator window, Function<Scalar, ? extends Tensor> function) {
    Tensor domain = OrderedQ.require(points.get(Tensor.ALL, 0));
    Tensor signal = points.get(Tensor.ALL, 1);
    BufferedImage bufferedImage = ImageFormat.of(Raster.of(SpectrogramArray.half_abs(signal, window), function));
    Clip clipX = Clips.interval(domain.Get(0), Last.of(domain));
    Scalar yhi = domain.Get(2).subtract(domain.Get(0)).reciprocal();
    Clip clipY = Clips.interval(yhi.zero(), yhi);
    return new ArrayPlot(bufferedImage, CoordinateBoundingBox.of(clipX, clipY));
  }

  /** Example:
   * <pre>
   * Spectrogram.of(vector, HannWindow.FUNCTION, ColorDataGradients.VISIBLESPECTRUM);
   * </pre>
   * 
   * @param vector
   * @param window for instance {@link HannWindow#FUNCTION}
   * @param function for instance {@link ColorDataGradients#VISIBLE_SPECTRUM}
   * @return array plot of the spectrogram of given vector with colors specified by given function
   * @throws Exception if input is not a vector */
  public static Tensor vector(Tensor vector, ScalarUnaryOperator window, Function<Scalar, ? extends Tensor> function) {
    return Raster.of(SpectrogramArray.half_abs(vector, window), function);
  }

  /** Example:
   * <pre>
   * Spectrogram.of(vector, HannWindow.FUNCTION);
   * </pre>
   * 
   * @param visualSet with single {@link VisualRow} containing domain and signal
   * @param window for instance {@link HannWindow#FUNCTION}
   * @return spectrogram chart of signal specified in given visual set generated using
   * given window function and {@link ColorDataGradients#VISIBLE_SPECTRUM} */
  public static Showable of(Tensor points, ScalarUnaryOperator window) {
    return of(points, window, ColorDataGradients.SUNSET_REVERSED);
  }

  /** @param visualSet with single {@link VisualRow} containing domain and signal
   * @return spectrogram chart of signal specified in given visual set generated using
   * {@link DirichletWindow} and {@link ColorDataGradients#VISIBLE_SPECTRUM} */
  public static Showable of(Tensor points) {
    return of(points, DirichletWindow.FUNCTION);
  }
}
