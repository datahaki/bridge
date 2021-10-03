// code by jph
package ch.alpine.java.gfx;

import ch.alpine.tensor.ExactTensorQ;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.alg.Dot;
import ch.alpine.tensor.mat.IdentityMatrix;
import ch.alpine.tensor.mat.Tolerance;
import ch.alpine.tensor.mat.re.Det;
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

  public void testTranslations() {
    Tensor xya = Tensors.vector(1, 2, 0);
    Tensor translate = GfxMatrix.translation(xya.extract(0, 2));
    assertEquals(GfxMatrix.of(xya), translate);
    ExactTensorQ.require(translate);
  }

  public void testFlipY() {
    Tensor tensor = GfxMatrix.flipY(5);
    ExactTensorQ.require(tensor);
    assertEquals(tensor, Tensors.fromString("{{1, 0, 0}, {0, -1, 5}, {0, 0, 1}}"));
    assertEquals(Det.of(tensor), RealScalar.ONE.negate());
  }
}
