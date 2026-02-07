// code by jph
package showcase.fig;

import ch.alpine.bridge.fig.ListPlot;
import ch.alpine.bridge.fig.Show;
import ch.alpine.bridge.pro.ShowProvider;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.alg.Flatten;
import ch.alpine.tensor.jet.EllipticCurve;
import ch.alpine.tensor.mat.UpperEvaluation;
import ch.alpine.tensor.num.GaussScalar;

class EllipticCurve3Demo implements ShowProvider {
  @Override
  public Show getShow() {
    int prime = 61;
    EllipticCurve ellipticCurve = EllipticCurve.of(GaussScalar.of(9, prime), GaussScalar.of(1, prime));
    Tensor all = Tensors.empty();
    for (int i = 0; i < prime; ++i)
      try {
        Tensor p = ellipticCurve.complete(GaussScalar.of(i, prime));
        all.append(p);
      } catch (Exception e) {
        // ---
      }
    Tensor matrix = UpperEvaluation.of(all, all, ellipticCurve::combine, s -> s);
    Tensor list = Tensor
        .of(Flatten.of(matrix, 1).stream().distinct().map(xy -> Tensors.vector(((GaussScalar) xy.Get(0)).number(), ((GaussScalar) xy.Get(1)).number())));
    Show show = new Show();
    show.add(ListPlot.of(list));
    show.setAspectRatio(RealScalar.ONE);
    return show;
  }

  static void main() {
    new EllipticCurve3Demo().run();
  }
}
