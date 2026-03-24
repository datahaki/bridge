// code by jph
package ch.alpine.bridge.fig;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.awt.Dimension;

import org.junit.jupiter.api.Test;

import ch.alpine.bridge.fig.plt.Plot;
import ch.alpine.tensor.num.Pi;
import ch.alpine.tensor.sca.Clips;
import ch.alpine.tensor.sca.tri.Sin;

class ShowTest {
  @Test
  void testFailNull() {
    assertThrows(Exception.class, () -> new Show(null));
  }

  @Test
  void testHighResolution() {
    Show show = new Show();
    show.add(Plot.of(Sin.FUNCTION, Clips.absolute(Pi.TWO), PlotOption.FILL));
    new Rasterize(show, new Dimension(4000, 4000)).image();
  }
}
