// code by jph
package ch.ethz.idsc.tensor.fig;

import org.jfree.chart.JFreeChart;

import ch.ethz.idsc.tensor.Tensor;
import ch.ethz.idsc.tensor.alg.Subdivide;
import ch.ethz.idsc.tensor.api.TensorUnaryOperator;
import ch.ethz.idsc.tensor.fft.PeriodogramArray;

public enum Periodogram {
  ;
  public static JFreeChart of(VisualSet _visualSet) {
    VisualSet visualSet = new VisualSet();
    TensorUnaryOperator tensorUnaryOperator = PeriodogramArray.of(512, 128);
    for (VisualRow _visualRows : _visualSet.visualRows()) {
      // Tensor domain = _visualRows.points().get(Tensor.ALL, 0);
      Tensor values = tensorUnaryOperator.apply(_visualRows.points().get(Tensor.ALL, 1));
      // System.out.println(Dimensions.of(values));
      // System.out.println(ArrayQ.of(values));
      visualSet.add(Subdivide.of(0, 1, values.length() - 1), values);
    }
    return ListPlot.of(visualSet, true);
  }
}
