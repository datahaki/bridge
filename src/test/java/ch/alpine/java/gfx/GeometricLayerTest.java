// code by jph
package ch.alpine.java.gfx;

import ch.alpine.java.util.AssertFail;
import ch.alpine.tensor.RealScalar;
import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;
import ch.alpine.tensor.alg.Dot;
import ch.alpine.tensor.lie.r2.CirclePoints;
import ch.alpine.tensor.sca.Chop;
import junit.framework.TestCase;

public class GeometricLayerTest extends TestCase {
  public void testSimple() {
    GeometricLayer geometricLayer = new GeometricLayer(GfxMatrix.translation(Tensors.vector(0, 0)));
    geometricLayer.popMatrix();
    AssertFail.of(() -> geometricLayer.popMatrix());
  }

  public void testPush() {
    Tensor a = GfxMatrix.translation(Tensors.vector(10, 10));
    GeometricLayer geometricLayer = new GeometricLayer(a);
    Tensor b = GfxMatrix.of(Tensors.vector(2, 3, 4));
    geometricLayer.pushMatrix(b);
    Chop._10.requireClose(Dot.of(a, b), geometricLayer.getMatrix());
    geometricLayer.toPoint2D(Tensors.vector(1, 2));
    geometricLayer.toPoint2D(1, 2);
    geometricLayer.toVector(Tensors.vector(1, 2));
    geometricLayer.toVector(1, 2);
    geometricLayer.toLine2D(Tensors.vector(1, 2));
    geometricLayer.toLine2D(Tensors.vector(1, 2), Tensors.vector(4, 1));
    geometricLayer.toPath2D(CirclePoints.of(10));
    geometricLayer.toPath2D(CirclePoints.of(10), false);
    geometricLayer.toPath2D(CirclePoints.of(10), true);
    float model2pixelWidth = geometricLayer.model2pixelWidth(3);
    double pixel2modelWidth = geometricLayer.pixel2modelWidth(model2pixelWidth);
    Chop._10.requireClose(RealScalar.of(pixel2modelWidth), RealScalar.of(3));
  }
}
