// code by jph
package ch.alpine.java.gfx;

import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.alg.Dot;
import ch.alpine.tensor.mat.IdentityMatrix;
import ch.alpine.tensor.mat.Tolerance;
import junit.framework.TestCase;

public class GfxMatrixTest extends TestCase {
  public void testSimple() {
    Tensor eye = Dot.of(GfxMatrix.flipY(100), GfxMatrix.flipY(100));
    assertEquals(eye, IdentityMatrix.of(3));
  }

  public void testXya() {
    Tensor xya = Tensors.vector(1, 2, 3);
    Tensor matrix = GfxMatrix.of(xya);
    Tensor vector = GfxMatrix.toVector(matrix);
    Tolerance.CHOP.requireClose(xya, vector);
  }
}
