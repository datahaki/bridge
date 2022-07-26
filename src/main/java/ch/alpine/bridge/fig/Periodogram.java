// code by jph
package ch.alpine.bridge.fig;

import java.util.NavigableSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.jfree.chart.JFreeChart;

import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.alg.Differences;
import ch.alpine.tensor.alg.Subdivide;
import ch.alpine.tensor.api.ScalarUnaryOperator;
import ch.alpine.tensor.ext.Integers;
import ch.alpine.tensor.fft.PeriodogramArray;
import ch.alpine.tensor.mat.Tolerance;
import ch.alpine.tensor.sca.exp.Log;

/** <p>inspired by
 * <a href="https://reference.wolfram.com/language/ref/Periodogram.html">Periodogram</a> */
public enum Periodogram {
  ;
  private static final Scalar _10 = RealScalar.of(10.0);

  /** @param visualSet with a single visual row
   * @param scaling of magnitude
   * @param windowLength for instance 512
   * @param offset for instance 128
   * @return */
  public static JFreeChart of(VisualSet visualSet, ScalarUnaryOperator scaling, int windowLength, int offset) {
    Integers.requireEquals(visualSet.visualRows().size(), 1);
    VisualRow visualRow = visualSet.visualRows().get(0);
    Tensor points = visualRow.points();
    windowLength = Math.min(points.length(), windowLength);
    {
      Tensor domain = points.get(Tensor.ALL, 0);
      //
      NavigableSet<Tensor> navigableSet = Differences.of(domain).stream() //
          .collect(Collectors.toCollection(TreeSet::new));
      Tolerance.CHOP.requireClose(navigableSet.first(), navigableSet.last());
    }
    Tensor signal = points.get(Tensor.ALL, 1);
    Tensor values = PeriodogramArray.of(windowLength, offset).apply(signal).map(scaling);
    VisualSet _visualSet = new VisualSet();
    _visualSet.setPlotLabel(visualSet.getPlotLabel());
    int h = values.length() / 2;
    VisualRow _visualRow = _visualSet.add(Subdivide.of(0, 0.5, h - 1), values.extract(0, h));
    _visualRow.setColor(visualRow.getColor());
    _visualRow.setLabel(visualRow.getLabelString());
    _visualRow.setStroke(visualRow.getStroke());
    return ListPlot.of(_visualSet, true);
  }

  /** uses the parameters
   * windowLength = 512
   * offset = 128
   * 
   * @param visualSet
   * @return */
  public static JFreeChart of(VisualSet visualSet) {
    return of(visualSet, Log.base(_10).andThen(_10::multiply), 512, 128);
  }
}
