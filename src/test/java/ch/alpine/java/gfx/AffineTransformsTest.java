// code by jph
package ch.alpine.java.gfx;

import static org.junit.jupiter.api.Assertions.fail;

import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;

import org.junit.jupiter.api.Test;

import ch.alpine.tensor.alg.Array;
import ch.alpine.tensor.mat.IdentityMatrix;

public class AffineTransformsTest {
  @Test
  public void testSimple() {
    AffineTransform affineTransform = AffineTransforms.of(Array.zeros(3, 3));
    try {
      affineTransform.createInverse();
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  @Test
  public void testEye() throws NoninvertibleTransformException {
    AffineTransform affineTransform = AffineTransforms.of(IdentityMatrix.of(3));
    affineTransform.createInverse();
  }
}
