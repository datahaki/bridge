// code by jph
package ch.alpine.bridge.demo.fig;

import java.awt.Container;
import java.awt.Font;

import javax.swing.JPanel;

import ch.alpine.bridge.fig.ShowComponent;
import ch.alpine.bridge.fig.ShowComponent.Option;
import ch.alpine.bridge.pro.ManipulateProvider;
import ch.alpine.bridge.ref.ann.ReflectionMarker;

@ReflectionMarker
class ShowComponentDemo implements ManipulateProvider {
  public Showcases showDemos = Showcases.Axes;
  public Font font = new JPanel().getFont();
  public Boolean xZoom = true;
  public Boolean xPan = true;
  public Boolean yZoom = true;
  public Boolean yPan = true;

  private final ShowComponent showComponent = new ShowComponent();
  @Override
  public Container getContainer() {
    showComponent.setFont(font);
    showComponent.setShow(showDemos.getShow());
    showComponent.setOptionX(Option.PAN, xPan);
    showComponent.setOptionX(Option.ZOOM, xZoom);
    showComponent.setOptionY(Option.PAN, yPan);
    showComponent.setOptionY(Option.ZOOM, yZoom);
    return showComponent;
  }

  static void main() {
    new ShowComponentDemo().runStandalone();
  }
}
