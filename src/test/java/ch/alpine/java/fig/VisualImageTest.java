// code by jph
package ch.alpine.java.fig;

import ch.alpine.tensor.alg.Array;
import ch.alpine.tensor.io.ImageFormat;
import ch.alpine.tensor.sca.Clips;
import junit.framework.TestCase;

public class VisualImageTest extends TestCase {
  public void testSimple() {
    VisualImage visualImage = new VisualImage(Clips.unit(), Clips.unit(), ImageFormat.of(Array.zeros(10, 20, 4)));
    visualImage.getAxisX();
  }

  public void testSparse() {
    VisualImage visualImage = new VisualImage(Clips.unit(), Clips.unit(), ImageFormat.of(Array.sparse(10, 20, 4)));
    visualImage.getPlotLabel();
  }
}
