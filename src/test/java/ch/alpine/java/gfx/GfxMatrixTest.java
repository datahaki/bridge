// code by jph
package ch.alpine.java.gfx;

import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.alg.Dot;
import ch.alpine.tensor.mat.IdentityMatrix;
import junit.framework.TestCase;

public class GfxMatrixTest extends TestCase {
  public void testSimple() {
    Tensor eye = Dot.of(GfxMatrix.flipY(100), GfxMatrix.flipY(100));
    assertEquals(eye, IdentityMatrix.of(3));
  }
}
