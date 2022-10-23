// code by jph
package demo.tensor.sca;

import java.io.IOException;

import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.io.Pretty;
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
        // Showable jFreeChart = ArrayPlot.of(matrix);
        // Show.export(HomeDirectory.Pictures( //
        // ChebyshevNodesDemo.class.getSimpleName() + chebyshevNodes + "C.png"), jFreeChart, new Dimension(400, 400));
      }
      {
        // Showable jFreeChart = ArrayPlot.of(Inverse.of(matrix));
        // Show.export(HomeDirectory.Pictures( //
        // ChebyshevNodesDemo.class.getSimpleName() + chebyshevNodes + "I.png"), jFreeChart, new Dimension(400, 400));
      }
    }
  }
}
