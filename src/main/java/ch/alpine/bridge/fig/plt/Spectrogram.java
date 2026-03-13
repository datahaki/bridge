// code by jph
package ch.alpine.bridge.fig.plt;

import java.awt.image.BufferedImage;
import java.util.function.Function;

import ch.alpine.bridge.fig.Showable;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.fft.SpectrogramArray;
import ch.alpine.tensor.fft.SpectrogramArrays;
import ch.alpine.tensor.img.ColorDataGradients;
import ch.alpine.tensor.img.Raster;
import ch.alpine.tensor.io.ImageFormat;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;
import ch.alpine.tensor.sca.Clips;

/** Remark:
 * Cepstrogram is not implemented because already covered by Spectrogram
 * {@link SpectrogramArrays#POWER}
 * 
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Spectrogram.html">Spectrogram</a>
 * <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Cepstrogram.html">Cepstrogram</a> */
public enum Spectrogram {
  ;
  /** @param spectrogramArray for example SpectrogramArrays.FOURIER.operator()
   * @param signal
   * @param sampleRate for instance 8000[s^-1]
   * @param function for instance ColorDataGradients.SUNSET_REVERSED
   * @return */
  public static Showable of( //
      SpectrogramArray spectrogramArray, //
      Tensor signal, //
      Scalar sampleRate, //
      Function<Scalar, ? extends Tensor> function) {
    BufferedImage bufferedImage = ImageFormat.of(Raster.of(spectrogramArray.half_abs(signal), function));
    ImagePlot imagePlot = ImagePlot.of(bufferedImage, CoordinateBoundingBox.of( //
        Clips.positive(RealScalar.of(signal.length()).divide(sampleRate)), //
        Clips.positive(sampleRate.divide(RealScalar.TWO))));
    imagePlot.setAspectRatioOne(false);
    return imagePlot;
  }

  /** @param spectrogramArray
   * @param signal
   * @param sampleRate for instance 8000[s^-1]
   * @return */
  public static Showable of(SpectrogramArray spectrogramArray, Tensor signal, Scalar sampleRate) {
    return of(spectrogramArray, signal, sampleRate, ColorDataGradients.SUNSET_REVERSED);
  }
}
