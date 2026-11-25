// code by jph
package ch.alpine.bridge.demo.fig;

import java.awt.Container;

import ch.alpine.bridge.fig.Show;
import ch.alpine.bridge.fig.ShowComponent;
import ch.alpine.bridge.fig.grd.AxisMaps;
import ch.alpine.bridge.fig.plt.ListPlot;
import ch.alpine.bridge.pro.ManipulateProvider;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.sca.Clip;
import ch.alpine.tensor.sca.Clips;

public class Log10Demo implements ManipulateProvider {
  public Clip clip = Clips.interval(1, 2);
  ShowComponent showComponent = new ShowComponent();

  @Override
  public Container getContainer() {
    Show show = new Show();
    show.add(ListPlot.of(Tensors.of(
        //
        Tensors.of(RealScalar.ZERO, clip.max()), //
        Tensors.of(RealScalar.ONE, clip.min())
    //
    )));
    show.showOptions.axisOptionsY.axisMap = AxisMaps.LOG10;
    showComponent.setShow(show);
    return showComponent;
  }

  static void main() {
    new Log10Demo().runStandalone();
  }
}
