// code by jph
package showcase.fig;

import ch.alpine.bridge.fig.Plot;
import ch.alpine.bridge.fig.Show;
import ch.alpine.bridge.pro.ShowProvider;
import ch.alpine.tensor.sca.Clip;
import ch.alpine.tensor.sca.Clips;
import ch.alpine.tensor.sca.tri.Cosh;
import ch.alpine.tensor.sca.tri.Sinhc;

class TrigDemo implements ShowProvider {
  @Override
  public Show getShow() {
    Show show = new Show();
    Clip clip = Clips.absolute(1e-7);
    show.add(Plot.of(Cosh.FUNCTION, clip));
    show.add(Plot.of(Sinhc.FUNCTION, clip));
    return show;
  }

  static void main() {
    new TrigDemo().run();
  }
}
