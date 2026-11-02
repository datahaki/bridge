// code by jph
package ch.alpine.bridge.geo;

import ch.alpine.bridge.fig.Show;
import ch.alpine.bridge.pro.ShowProvider;
import ch.alpine.tensor.qty.Degree;
import ch.alpine.tensor.qty.Quantity;

public class GeoShow implements ShowProvider {
  @Override
  public Show getShow() {
    Show show = new Show();
    TilePixel tilePixel = TilePixel.from(7, 256, Quantity.of(38.343373, Degree.UNIT), Quantity.of(-0.762800, Degree.UNIT));
    show.add(GeoGraphics.of(tilePixel));
    return show;
  }

  static void main() {
    new GeoShow().runStandalone();
  }
}
