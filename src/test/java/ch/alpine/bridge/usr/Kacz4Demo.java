// code by jph
package ch.alpine.bridge.usr;

import ch.alpine.bridge.fig.ListPlot;
import ch.alpine.bridge.fig.Show;
import ch.alpine.bridge.fig.ShowDialog;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.mat.pi.LeastSquares;
import ch.alpine.tensor.mat.re.KaczmarzIteration;
import ch.alpine.tensor.nrm.Vector2Norm;
import ch.alpine.tensor.pdf.Distribution;
import ch.alpine.tensor.pdf.RandomVariate;
import ch.alpine.tensor.pdf.c.UniformDistribution;
import ch.alpine.tensor.sca.N;
import ch.alpine.tensor.sca.exp.Log10;

public enum Kacz4Demo {
  ;
  public static void main(String[] args) {
    Distribution distribution = UniformDistribution.of(-2, 2);
    int n = 3;
    Tensor matrix = RandomVariate.of(distribution, n, n + 100);
    Tensor b = RandomVariate.of(distribution, n);
    Tensor sol = LeastSquares.of(matrix, b);
    System.out.println(sol.map(N.DOUBLE));
    Show show = new Show();
    {
      KaczmarzIteration kaczmarzIteration = new KaczmarzIteration(matrix, b);
      Tensor points = Tensors.empty();
      for (int i = 0; i < 30; ++i) {
        Tensor x = kaczmarzIteration.refine();
        Scalar err = Vector2Norm.between(x, sol);
        points.append(Tensors.of(RealScalar.of(i), err));
        if (i == 29)
          System.out.println(err);
      }
      points.set(Log10.FUNCTION, Tensor.ALL, 1);
      show.add(ListPlot.of(points)).setLabel("ordered");
    }
    ShowDialog.of(show);
  }
}
