// code by jph
package demo.tensor.pdf;

import java.awt.Dimension;
import java.io.IOException;

import ch.alpine.bridge.fig.ListPlot;
import ch.alpine.bridge.fig.Show;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.alg.Subdivide;
import ch.alpine.tensor.ext.HomeDirectory;
import ch.alpine.tensor.sca.erf.Erfc;

public enum ErfcDemo {
  ;
  public static void main(String[] args) throws IOException {
    Tensor domain = Subdivide.of(-5, 5, 300);
    Show show = new Show();
    show.add(new ListPlot(domain, domain.map(Erfc.FUNCTION)));
    show.export(HomeDirectory.Pictures(Erfc.class.getSimpleName() + ".png"), new Dimension(640, 480));
  }
}
