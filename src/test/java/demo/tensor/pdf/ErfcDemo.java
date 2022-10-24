// code by jph
package demo.tensor.pdf;

import java.awt.Dimension;
import java.io.IOException;

import ch.alpine.bridge.fig.Plot;
import ch.alpine.bridge.fig.Show;
import ch.alpine.tensor.ext.HomeDirectory;
import ch.alpine.tensor.sca.Clip;
import ch.alpine.tensor.sca.Clips;
import ch.alpine.tensor.sca.erf.Erfc;

public enum ErfcDemo {
  ;
  public static void main(String[] args) throws IOException {
    Clip clip = Clips.absolute(5);
    // Tensor domain = Subdivide.of(-5, 5, 300);
    Show show = new Show();
    show.add(Plot.of(Erfc.FUNCTION, clip));
    show.export(HomeDirectory.Pictures(Erfc.class.getSimpleName() + ".png"), new Dimension(640, 480));
  }
}
