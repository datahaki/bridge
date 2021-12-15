// code by jph
package ch.alpine.java.util;

import java.util.Map;

import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.alg.Flatten;
import ch.alpine.tensor.img.ImageResize;
import ch.alpine.tensor.io.ResourceData;
import ch.alpine.tensor.red.Tally;
import junit.framework.TestCase;

public class ImageResizeTest extends TestCase {
  public void testSimple() {
    Tensor tensor = ResourceData.of("/image/checkbox/gentleface/48/1.png");
    Tensor smallr = ImageResize.of(tensor, 8, 8);
    Map<Tensor, Long> map = Tally.of(Flatten.of(smallr.get(Tensor.ALL, Tensor.ALL, 3)));
    assertTrue(0 < map.get(RealScalar.ZERO));
  }
}
