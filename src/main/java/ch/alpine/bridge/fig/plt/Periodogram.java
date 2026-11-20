// code by jph
package ch.alpine.bridge.fig.plt;

import java.util.NavigableSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import ch.alpine.bridge.fig.Showable;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.alg.Differences;
import ch.alpine.tensor.alg.Subdivide;
import ch.alpine.tensor.fft.PeriodogramArray;
import ch.alpine.tensor.fft.SpectrogramArrays;
import ch.alpine.tensor.mat.Tolerance;

/** <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Periodogram.html">Periodogram</a> */
public enum Periodogram {
  ;
  /** @param visualSet with a single visual row
   * @param scaling of magnitude
   * @param windowLength for instance 512
   * @param offset for instance 128
   * @return */
  public static Showable of(Tensor points, int windowLength, int offset) {
    windowLength = Math.min(points.length(), windowLength);
    {
      Tensor domain = points.get(Tensor.ALL, 0);
      NavigableSet<Tensor> navigableSet = Differences.of(domain).stream() //
          .collect(Collectors.toCollection(TreeSet::new));
      Tolerance.CHOP.requireClose(navigableSet.first(), navigableSet.last());
    }
    Tensor signal = points.get(Tensor.ALL, 1);
    Tensor values = new PeriodogramArray(SpectrogramArrays.FOURIER.operator()).config(windowLength, offset).apply(signal);
    int h = values.length() / 2;
    return ListLinePlot.of(Subdivide.of(0, 0.5, h - 1), values.extract(0, h));
  }

  /** uses the parameters
   * windowLength = 512
   * offset = 128
   * 
   * @param visualSet
   * @return */
  public static Showable of(Tensor points) {
    return of(points, 512, 128);
  }
}
