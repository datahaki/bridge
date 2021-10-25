// code by jph
package ch.alpine.java.gfx;

import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;

import ch.alpine.tensor.alg.Array;
import ch.alpine.tensor.mat.IdentityMatrix;
import junit.framework.TestCase;

public class AffineTransformsTest extends TestCase {
  public void testSimple() {
    AffineTransform affineTransform = AffineTransforms.of(Array.zeros(3, 3));
    try {
      affineTransform.createInverse();
      fail();
    } catch (Exception exception) {
      // ---
    }
  }

  public void testEye() throws NoninvertibleTransformException {
    AffineTransform affineTransform = AffineTransforms.of(IdentityMatrix.of(3));
    affineTransform.createInverse();
  }
}
