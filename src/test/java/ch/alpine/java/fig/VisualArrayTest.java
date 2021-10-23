// code by jph
package ch.alpine.java.fig;

import ch.alpine.tensor.alg.Array;
import ch.alpine.tensor.io.ImageFormat;
import ch.alpine.tensor.sca.Clips;
import junit.framework.TestCase;

public class VisualArrayTest extends TestCase {
  public void testSimple() {
    VisualArray visualArray = new VisualArray(Clips.unit(), Clips.unit(), ImageFormat.of(Array.zeros(10, 20, 4)));
    visualArray.getAxisX();
  }

  public void test2() {
    VisualArray visualArray = new VisualArray(Clips.unit(), Clips.unit(), ImageFormat.of(Array.sparse(10, 20, 4)));
    visualArray.getPlotLabel();
  }
}
