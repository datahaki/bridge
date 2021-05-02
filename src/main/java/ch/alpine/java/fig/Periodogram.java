// code by jph
package ch.alpine.java.fig;

import org.jfree.chart.JFreeChart;

import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.alg.Subdivide;
import ch.alpine.tensor.api.TensorUnaryOperator;
import ch.alpine.tensor.fft.PeriodogramArray;

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
