// code by jph
package showcase.fig;

import java.util.List;
import java.util.stream.Stream;

import ch.alpine.bridge.fig.ListLinePlot;
import ch.alpine.bridge.fig.ListPlot;
import ch.alpine.bridge.fig.ReImPlot;
import ch.alpine.bridge.fig.Show;
import ch.alpine.bridge.pro.ShowProvider;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Scalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.jet.EllipticCurve;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;
import ch.alpine.tensor.sca.Clip;
import ch.alpine.tensor.sca.Clips;

class EllipticCurveDemo implements ShowProvider {
  @Override
  public Show getShow() {
    EllipticCurve ellipticCurve = EllipticCurve.of(0, 17);
    List<Tensor> list = Stream.of("{-2, 3}", "{-1, 4}", "{+2, 5}", "{+4, 9}", "{+8, 23}", "{43, 282}", "{52, 375}", "{5234, 378661}") //
        .map(Tensors::fromString).toList();
    Tensor p = list.get(0);
    Tensor q = list.get(1);
    Tensor all = Tensors.of(p, q);
    for (int i = 0; i < 2; ++i) {
      q = ellipticCurve.combine(p, q);
      all.append(q);
    }
    Scalar min = ellipticCurve.polynomial().roots().Get(0);
    Clip clip = Clips.interval(min.subtract(RealScalar.of(4)), RealScalar.of(10));
    Show show = new Show();
    show.add(ReImPlot.of(ellipticCurve, clip));
    show.add(ReImPlot.of(s -> ellipticCurve.apply(s).negate(), clip));
    show.add(ListPlot.of(all));
    show.setCbb(CoordinateBoundingBox.of(clip, Clips.absolute(8)));
    show.setAspectRatio(RealScalar.ONE);
    show.add(ListLinePlot.of(all));
    return show;
  }

  static void main() {
    new EllipticCurveDemo().run();
  }
}
