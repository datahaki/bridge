// code by jph
package ch.alpine.bridge.gfx;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.geom.Point2D;

import org.junit.jupiter.api.Test;

import ch.alpine.tensor.Tensor;
import ch.alpine.tensor.Tensors;

class AffineFrame2DTest {
  @Test
  void testSimple() {
    Tensor m1 = GfxMatrix.of(Tensors.vector(1, 2, 3));
    Tensor m2 = GfxMatrix.of(Tensors.vector(-.3, 0.2, .4));
    AffineFrame2D af2 = new AffineFrame2D(m1);
    AffineFrame2D af3 = af2.dot(m2);
    assertEquals(af3.matrix_copy(), m1.dot(m2));
    Point2D point2d = af3.toPoint2D();
    Point2D actual = new Point2D.Double(1.2687737473681602, 1.7596654982619508);
    assertTrue(point2d.distance(actual) < 1e-9);
    assertTrue(point2d.distance(af3.toPoint2D(0, 0)) < 1e-9);
  }

  @Test
  void testPoint() {
    Tensor m1 = GfxMatrix.of(Tensors.vector(1, 2, 3));
    AffineFrame2D af2 = new AffineFrame2D(m1);
    Tensor v = Tensors.vector(-.3, -.4, 1);
    Point2D p = af2.toPoint2D(v.Get(0).number().doubleValue(), v.Get(1).number().doubleValue());
    Tensor q = m1.dot(v);
    assertEquals(p.getX(), q.Get(0).number().doubleValue());
    assertEquals(p.getY(), q.Get(1).number().doubleValue());
  }
}
