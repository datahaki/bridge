// code by jph
package ch.alpine.bridge.demo.fig;

import java.awt.Dimension;
import java.io.IOException;

import ch.alpine.bridge.fig.Rasterize;
import ch.alpine.bridge.fig.Show;
import ch.alpine.tensor.ext.HomeDirectory;

enum ReadmeGen {
  ;
  static void main() throws IOException {
    Show show = Showcases.Relief2.getShow();
    Rasterize rasterize = new Rasterize(show, new Dimension(480, 240));
    rasterize.export(HomeDirectory.Pictures.resolve("relief.png"));
  }
}
