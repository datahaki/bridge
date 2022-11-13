// code by jph
package ch.alpine.bridge.fig;

import java.awt.image.BufferedImage;
import java.util.function.Function;

import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.api.ScalarUnaryOperator;
import ch.alpine.tensor.fft.XtrogramArray;
import ch.alpine.tensor.img.ColorDataGradients;
import ch.alpine.tensor.img.Raster;
import ch.alpine.tensor.io.ImageFormat;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;
import ch.alpine.tensor.sca.Clips;
import ch.alpine.tensor.sca.win.DirichletWindow;
import ch.alpine.tensor.sca.win.HannWindow;

/** inspired by
 * <a href="https://reference.wolfram.com/language/ref/Spectrogram.html">Spectrogram</a> */
public enum Spectrogram {
  ;
  /** Remark: the unit of the signal is in the color not the axis
   * 
   * @param signal
   * @param sampleRate for instance 8000[s^-1]
   * @param window for instance {@link HannWindow#FUNCTION}
   * @param function for instance {@link ColorDataGradients#VISIBLE_SPECTRUM}
   * @return */
  public static Showable of(Tensor signal, Scalar sampleRate, ScalarUnaryOperator window, Function<Scalar, ? extends Tensor> function) {
    BufferedImage bufferedImage = ImageFormat.of(Raster.of(XtrogramArray.Spectrogram.half_abs(signal, window), function));
    return ImagePlot.of(bufferedImage, CoordinateBoundingBox.of( //
        Clips.positive(RealScalar.of(signal.length()).divide(sampleRate)), //
        Clips.positive(sampleRate.divide(RealScalar.TWO))));
  }

  /** Example:
   * <pre>
   * Spectrogram.of(vector, HannWindow.FUNCTION);
   * </pre>
   * 
   * @param points
   * @param window for instance {@link HannWindow#FUNCTION}
   * @return spectrogram chart of signal specified in given visual set generated using
   * given window function and {@link ColorDataGradients#VISIBLE_SPECTRUM} */
  public static Showable of(Tensor signal, Scalar sampleRate, ScalarUnaryOperator window) {
    return of(signal, sampleRate, window, ColorDataGradients.SUNSET_REVERSED);
  }

  /** @param points
   * @return spectrogram chart of signal specified in given visual set generated using
   * {@link DirichletWindow} and {@link ColorDataGradients#VISIBLE_SPECTRUM} */
  public static Showable of(Tensor signal, Scalar sampleRate) {
    return of(signal, sampleRate, DirichletWindow.FUNCTION);
  }
}
