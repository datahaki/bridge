// code by jph
package showcase.fig;

import ch.alpine.bridge.fig.ReImPlot;
import ch.alpine.bridge.fig.Show;
import ch.alpine.bridge.fig.ShowDialog;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.jet.EllipticCurve;
import ch.alpine.tensor.num.Prime;
import ch.alpine.tensor.opt.nd.CoordinateBoundingBox;
import ch.alpine.tensor.sca.Clip;
import ch.alpine.tensor.sca.Clips;

/* package */ enum EllipticCurve2Demo {
  ;
  static void main() {
    Clip clip = Clips.interval(-10, 10);
    Show show = new Show();
    for (int i = 1; i < 10; i++) {
      EllipticCurve ellipticCurve = EllipticCurve.of(RealScalar.ZERO, Prime.of(i));
      show.add(ReImPlot.of(ellipticCurve, clip));
    }
    // show.add(ReImPlot.of(s -> ellipticCurve.apply(s).negate(), clip));
    show.setCbb(CoordinateBoundingBox.of(clip, Clips.absolute(8)));
    show.setAspectRatio(RealScalar.ONE);
    ShowDialog.of(show);
  }
}
