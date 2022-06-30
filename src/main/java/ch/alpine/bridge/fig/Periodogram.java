// code by jph
package ch.alpine.bridge.fig;

import java.util.NavigableSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.jfree.chart.JFreeChart;

import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.alg.Differences;
import ch.alpine.tensor.alg.Subdivide;
import ch.alpine.tensor.ext.Integers;
import ch.alpine.tensor.fft.PeriodogramArray;
import ch.alpine.tensor.mat.Tolerance;

public enum Periodogram {
  ;
  /** @param visualSet with a single visual row
   * @param windowLength for instance 512
   * @param offset for instance 128
   * @return */
  public static JFreeChart of(VisualSet visualSet, int windowLength, int offset) {
    Integers.requireEquals(visualSet.visualRows().size(), 1);
    VisualRow visualRow = visualSet.visualRows().get(0);
    Tensor points = visualRow.points();
    {
      Tensor domain = points.get(Tensor.ALL, 0);
      NavigableSet<Tensor> navigableSet = Differences.of(domain).stream() //
          .distinct() //
          .collect(Collectors.toCollection(TreeSet::new));
      Tolerance.CHOP.requireClose(navigableSet.first(), navigableSet.last());
    }
    Tensor signal = points.get(Tensor.ALL, 1);
    Tensor values = PeriodogramArray.of(windowLength, offset).apply(signal);
    VisualSet _visualSet = new VisualSet();
    _visualSet.add(Subdivide.of(0, 1, values.length() - 1), values);
    return ListPlot.of(_visualSet, true);
  }

  /** uses the parameters
   * windowLength = 512
   * offset = 128
   * 
   * @param visualSet
   * @return */
  public static JFreeChart of(VisualSet visualSet) {
    return of(visualSet, 512, 128);
  }
}
