// code by jph
package ch.alpine.java.gfx;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;

import org.junit.jupiter.api.Test;

import ch.alpine.tensor.alg.Array;
import ch.alpine.tensor.mat.IdentityMatrix;

class AffineTransformsTest {
  @Test
  public void testSimple() {
    AffineTransform affineTransform = AffineTransforms.of(Array.zeros(3, 3));
    assertThrows(Exception.class, () -> affineTransform.createInverse());
  }

  @Test
  public void testEye() throws NoninvertibleTransformException {
    AffineTransform affineTransform = AffineTransforms.of(IdentityMatrix.of(3));
    affineTransform.createInverse();
  }
}
