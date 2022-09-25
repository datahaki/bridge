// code by jph
package demo.tensor.sca;

import java.io.IOException;

import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;

import ch.alpine.bridge.fig.ArrayPlot;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.ext.HomeDirectory;
import ch.alpine.tensor.io.Pretty;
import ch.alpine.tensor.mat.re.Inverse;
import ch.alpine.tensor.sca.Round;
import ch.alpine.tensor.sca.ply.ChebyshevNodes;

public enum ChebyshevNodesDemo {
  ;
  public static void main(String[] args) throws IOException {
    int n = 64;
    for (ChebyshevNodes chebyshevNodes : ChebyshevNodes.values()) {
      Tensor matrix = chebyshevNodes.matrix(n);
      {
        System.out.println(Pretty.of(matrix.map(Round._3)));
        JFreeChart jFreeChart = ArrayPlot.of(matrix);
        ChartUtils.saveChartAsPNG(HomeDirectory.Pictures( //
            ChebyshevNodesDemo.class.getSimpleName() + chebyshevNodes + "C.png"), jFreeChart, 400, 400);
      }
      {
        JFreeChart jFreeChart = ArrayPlot.of(Inverse.of(matrix));
        ChartUtils.saveChartAsPNG(HomeDirectory.Pictures( //
            ChebyshevNodesDemo.class.getSimpleName() + chebyshevNodes + "I.png"), jFreeChart, 400, 400);
      }
    }
  }
}
