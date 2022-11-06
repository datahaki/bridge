// code by jph
package ch.alpine.bridge.usr;

import ch.alpine.bridge.fig.ListPlot;
import ch.alpine.bridge.fig.Show;
import ch.alpine.bridge.fig.ShowDialog;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.mat.re.KaczmarzIteration;
import ch.alpine.tensor.nrm.Vector2Norm;
import ch.alpine.tensor.sca.N;
import ch.alpine.tensor.sca.exp.Log10;

public enum Kacz3Demo {
  ;
  public static void main(String[] args) {
    Tensor matrix = Tensors.fromString("{{3,2,1,0},{2,4,2,3}}");
    Tensor b = Tensors.vector(4, 7).map(N.DOUBLE);
    Show show = new Show();
    int n = 30;
    {
      KaczmarzIteration kaczmarzIteration = new KaczmarzIteration(matrix, b);
      Tensor points = Tensors.empty();
      for (int i = 0; i < n; ++i) {
        Tensor x = kaczmarzIteration.refine();
        Scalar err = Vector2Norm.of(matrix.dot(x).subtract(b));
        points.append(Tensors.of(RealScalar.of(i), err));
      }
      points.set(Log10.FUNCTION, Tensor.ALL, 1);
      show.add(ListPlot.of(points)).setLabel("ordered");
    }
    {
      KaczmarzIteration kaczmarzIteration = KaczmarzIteration.of(matrix, b);
      Tensor points = Tensors.empty();
      for (int i = 0; i < n; ++i) {
        Tensor x = kaczmarzIteration.refine();
        Scalar err = Vector2Norm.of(matrix.dot(x).subtract(b));
        points.append(Tensors.of(RealScalar.of(i), err));
      }
      points.set(Log10.FUNCTION, Tensor.ALL, 1);
      show.add(ListPlot.of(points)).setLabel("ordered");
    }
    ShowDialog.of(show);
  }
}
