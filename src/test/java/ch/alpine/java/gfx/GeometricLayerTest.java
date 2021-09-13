// code by jph
package ch.alpine.java.gfx;

import ch.alpine.java.util.AssertFail;
import ch.alpine.tensor.Tensors;
import junit.framework.TestCase;

public class GeometricLayerTest extends TestCase {
  public void testSimple() {
    GeometricLayer geometricLayer = new GeometricLayer(GfxMatrix.translation(Tensors.vector(0, 0)));
    geometricLayer.popMatrix();
    AssertFail.of(() -> geometricLayer.popMatrix());
  }

  public void testPush() {
    GeometricLayer geometricLayer = new GeometricLayer(GfxMatrix.translation(Tensors.vector(10, 10)));
    geometricLayer.pushMatrix(GfxMatrix.of(Tensors.vector(2, 3, 4)));
  }
}
