// code by jph
package ch.alpine.bridge.fig;

import java.util.function.Function;

import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.alg.OrderedQ;
import ch.alpine.tensor.api.ScalarUnaryOperator;
import ch.alpine.tensor.ext.Integers;
import ch.alpine.tensor.fft.SpectrogramArray;
import ch.alpine.tensor.img.ColorDataGradients;
import ch.alpine.tensor.img.Raster;
import ch.alpine.tensor.io.ImageFormat;
import ch.alpine.tensor.qty.CompatibleUnitQ;
import ch.alpine.tensor.qty.Unit;
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
  public static Showable of(Show visualSet, ScalarUnaryOperator window, Function<Scalar, ? extends Tensor> function) {
    Integers.requireEquals(visualSet.visualRows().size(), 1);
    VisualRow visualRow = visualSet.getVisualRow(0);
    String string = visualRow.getLabelString();
    if (!string.isBlank())
      System.err.println("spectrogram drops row label");
    Tensor domain = OrderedQ.require(visualRow.points().get(Tensor.ALL, 0));
    Tensor signal = visualRow.points().get(Tensor.ALL, 1);
    Unit unit = visualSet.getAxisY().getUnit();
    Scalar yhi = domain.Get(2).subtract(domain.Get(0)).reciprocal();
    if (!CompatibleUnitQ.SI().with(unit).test(yhi))
      visualSet.getAxisY().setUnit(visualSet.getAxisX().getUnit().negate());
    StaticHelper.create( //
        ImageFormat.of(Raster.of(SpectrogramArray.half_abs(signal, window), function)), //
        visualSet, domain, yhi);
    return null;
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
  public static Tensor of(Tensor vector, ScalarUnaryOperator window, Function<Scalar, ? extends Tensor> function) {
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
  public static Showable of(Show visualSet, ScalarUnaryOperator window) {
    return of(visualSet, window, ColorDataGradients.SUNSET_REVERSED);
  }

  /** @param visualSet with single {@link VisualRow} containing domain and signal
   * @return spectrogram chart of signal specified in given visual set generated using
   * {@link DirichletWindow} and {@link ColorDataGradients#VISIBLE_SPECTRUM} */
  public static Showable of(Show visualSet) {
    return of(visualSet, DirichletWindow.FUNCTION);
  }
}
